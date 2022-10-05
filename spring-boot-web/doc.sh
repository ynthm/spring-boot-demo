#!/bin/bash

# 生成html
mvn -Dfile.encoding=UTF-8 smart-doc:html
# 生成markdown
mvn -Dfile.encoding=UTF-8 smart-doc:markdown
# 生成adoc
mvn -Dfile.encoding=UTF-8 smart-doc:adoc