# Devo Challenge 3

* Description

Tf/idf (term frequency / inverse document frequency) is an statistic that reflects the importance of a term T in
a document D (or the relevance of a document for a searched term) relative to a document set S.
 
See https://en.wikipedia.org/wiki/Tf%E2%80%93idf 

Tf/idf can be extended to a set of terms TT adding the tf/idf for each term.
 
Assume that we have a directory D containing a document set S, with one file per document. Documents will be
added to that directory by external agents,but they will never be removed or overwritten. We are given a set of
terms TT, and asked to compute the tf/idf of TT for each document in D, and report the N top documents sorted by
relevance.

The program must run as a daemon/service that is watching for new documents, and dynamically updates
the computed tf/idf for each document and the inferred ranking.

The program will run with the parameters:

- The directory D where the documents will be written.<br>
- The terms TT to be analyzed.<br>
- The count N of top results to show.<br>
- The period P to report the top N. 

For example: ./tdIdf -d dir -n 5 -p 300 -t &quot;password try again&quot; ...<br>

Result examples:<br>
doc1.txt 0.78<br>
doc73.txt 0.76<br>

* Build instructions

At the root of the project directory, execute the command "mvn clean package". The executable jar will be
created at the "target" directory.

* Running the demo

To run the demo, execute the command "java -jar <jar_file_name> <args...> at the directory where the generated jar
file is.
Some demo data files are included at the directory /data

* Complexity analysis

CorpusBuilder has complexity O(n), it's a simple loop reading each line and from each line each word and adding to the
document corpus.

DocumentCorpus has complexity O(1) for term insertion and term counting, and complexity O(n) for counting total terms.

TfIdfCalculator has complexity O(n) for computing the tf-idf of each term.
    