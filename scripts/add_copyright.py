#!/usr/local/bin/python

import re
import sys
import os
import getopt
import math
import tempfile
import stat
import subprocess
from optparse import OptionParser
from glob import glob
import shlex


watermark = "".join(["==__" for i in range(80/4)])

# Copyright comments
java_apache = """
/* %s 
 * Comment 3. 
 * %s 
 */
""" % (watermark, watermark)

py_apache = """
# %s
# Comment
# %s
""" % (watermark, watermark)

xml_apache = """
<!-- %s 
Comment
%s -->
""" % (watermark, watermark)

# Regexes to match copyright comments.
java_regex = r"""
/\* %s 
 \* .* 
 \* %s 
 \*/
""" % (watermark, watermark)

py_regex = r"""
# %s
# .*
# %s
""" % (watermark, watermark)

xml_regex = r"""
<!-- %s 
.*
%s -->
""" % (watermark, watermark)

def get_root_dir():
    scripts_dir =  os.path.abspath(sys.path[0])
    root_dir =  os.path.dirname(os.path.dirname(scripts_dir))
    print "Using root_dir: " + root_dir
    return root_dir;

class Commenter:
    
    def __init__(self, options):
        self.root_dir = os.path.abspath(get_root_dir())
        self.cr_comments = {}
        self.cr_regexes = {}
        self.add_copyright("java", java_apache, java_regex)
        self.add_copyright("py", py_apache, py_regex)
        #self.add_copyright("xml", xml_apache, xml_regex)
                
    def add_copyright(self, type, comment, regex):
        assert watermark in comment
        
        comment = comment.strip() + "\n\n"
        regex = re.compile(regex.strip() + "\n\n")
        self.cr_comments[type] = comment
        self.cr_regexes[type] = regex
    
    def copyright_dir(self, top_dir):
        for name in os.listdir(top_dir):
            path = os.path.join(top_dir, name)
            if os.path.isdir(path) and name != ".svn" and name != ".git":
                self.copyright_dir(path)
            else:
                self.copyright_file(path)
                
    def copyright_file(self, path):
        re.compile("")
        name = os.path.basename(path)        
        type = re.sub(r".+\.([^\.]+)", r"\1", name)
        if type not in self.cr_comments.keys(): #["java", "py"]:
            print "Skipping:", path
            return
        
        comment = self.cr_comments[type]
        regex = self.cr_regexes[type]
        
        with open(path,'r+') as f:
            content = f.read()
            
            match = regex.match(content)
            if match:
                content = content[:match.start()] + content[match.end():]                
            elif watermark in content:
                print "WARN: file contains watermark but regex didn't match:", path
                                            
            f.seek(0)
            prefix = ""
            tmp = ""
            if type == "py":
                for line in content.split("\n"):
                    if line.startswith("#!"):
                        prefix += line + "\n"
                    else:
                        break
            content = content[len(prefix):]
            f.write(prefix + comment + content)
        
        
            
        
        
if __name__ == "__main__":
    usage = "%prog [top_dir...]"

    parser = OptionParser(usage=usage)
    #parser.add_option('-f', '--fast', action="store_true", help="Run a fast version")
    (options, args) = parser.parse_args(sys.argv)

    if len(args) < 2:
        parser.print_help()
        sys.exit(1)
    
    commenter = Commenter(options)
    for top in args[1:]:
        if os.path.isdir(top):
            commenter.copyright_dir(top)
        elif os.path.isfile(top):
            commenter.copyright_file(top)
        else:
            print "Skipping:", top
