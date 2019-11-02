#### Storm basics

* **Topology** - consists of nodes and edges
* **Nodes** - represent either spouts or bolts
* **Edges** - represent streams of tuples between these spouts and bolts
* **Tuple** - is an ordered list of values, where each value is assigned a name
* **Stream** - is an unbounded sequence of tuples between a spout and a bolt or between two bolts
* **Spout** - is the source of a stream in a topology, usually listening to some sort of live feed of data
* **Bolt** - accepts a stream of tuples from a spout or another bolt, typically performing some sort of computation or transformation on these input tuples. The bolt can then optionally emit new tuples that serve as the input stream to another bolt in the topology
* **Spout & Bolt** - have one or many individual instances that perform all of this processing in parallel
