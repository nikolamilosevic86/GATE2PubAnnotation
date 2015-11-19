# GATE2PubAnnotation

Tool that transforms GATE annotated documents into PubAnnotations. This tool is written in Java. 

#Features

* GATE2PubAnnotation currently supports only conversion of denotations into PubAnnotation, but not relationships (co-reference)

# How to run

GATEToPubAnnotation takes 3 parameters. The first one is file to GATE annotated file. The second is sourcedb (Source database) and the third one is sourceid (ID of the document in the database).

Command line example:
``` 
java -jar GATE2PubAnnotation.jar example/example.xml PMC PMC125532
```

# Useful links and tools

* PubAnnotation: http://www.pubannotation.org/
* GATE: https://gate.ac.uk
* Text Annotation Editor: http://textae.pubannotation.org/
