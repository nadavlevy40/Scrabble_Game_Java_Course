
# Book Scrabble - Overview

This Book Scrabble Project is the final project of the course Advanced Software Development 2.
The project is a client-server based application that allows users to play the similar game of Book Scrabble.
The server side of the application is based on SOLID programming principles and includes optimization algorithms and design patterns to ensure high performance and scalability.

Currently, our team is working on all aspects of the client side, including the GUI using JavaFX. We are using the user stories burning method to ensure that we are building the features that are most important to our users first.

In addition to the development work, we are also focused on creating comprehensive documentation for the project. This includes a Gantt chart to help manage timelines and ensure that we are meeting our goals on schedule, as well as testing and other documentation to ensure that the application is robust and reliable.



## The Team

* Nadav Levy
* Dana Gabay
* Hila Peled
* Agam Medina

## Gantt Chart

![Gantt Chart](./Model(1).png)



## Getting Started

To use this project, you will need to have the following dependencies installed:

* java


## Cache Manager

The CacheManager class is responsible for maintaining a cache of words that have been previously searched for. It uses a LinkedHashMap to store the words and their counts, and a CacheReplacementPolicy to determine which word to remove from the cache if the size exceeds the maximum limit.

## Cache Replacement Policy

The CacheReplacementPolicy is an interface that defines two methods: add and remove. Each class that implements this interface must implement these methods in order to specify how the cache should be managed.

## Bloom Filter

The BloomFilter class is a probabilistic data structure that is used to determine if an element is a member of a set. It is designed to be space-efficient and to have a high probability of returning accurate results. In this project, it is used to check if a word exists in the list of files.

## IOSearcher

The IOSearcher class is responsible for performing a deep search over the list of files to check if a word exists. It uses a FileReader and a BufferedReader to read the files line by line, and splits the lines into words using the split method. If the word is found, it returns true.

## Additional Classes

* LRU and LFU are classes that implement the CacheReplacementPolicy interface and specify different replacement strategies for the cache.

* MD5 and SHA1 are classes that implement the HashFunction interface and specify different hash functions that can be used by the bloom filter.

## Design Patterns

This server-side program uses the following design patterns:

* Factory pattern: The HashFunction interface and its implementing classes (MD5 and SHA1) can be considered as a simple example of the factory pattern, as the BloomFilter class uses them to create instances of the desired hash function.

* Strategy pattern: The CacheReplacementPolicy interface and its implementing classes (LRU and LFU) can be considered as an example of the strategy pattern, as they specify different strategies for managing the cache. The CacheManager class uses these strategies to determine which word to remove from the cache when the size exceeds the maximum limit.

* Proxy / Flyweight : The DictionaryManager class creates new dictionaries by demand only , using HashMap to determine whether requested dictionary is already exist.

## Architecture

The program follows a three-layer architecture, with the following layers:

1.  layer: The Dictionary class acts as the presentation layer, providing a simple interface for users to query the dictionary and challenge negative results.

2. Business logic layer: The CacheManager, BloomFilter, and IOSearcher classes belong to this layer, as they contain the core functionality of the dictionary.

3. Data access layer: The FileReader and BufferedReader classes belong to this layer, as they are responsible for reading the files and accessing the data.
## Data Structures

* LinkedHashMap: The CacheManager class uses a LinkedHashMap to store the words and their counts in the cache.

* Array: The BloomFilter class uses an array to store the hash values of the words that have been added to the filter.

## Algorithms

* Hash functions: The MD5 and SHA1 classes implement well-known hash functions that are used by the bloom filter to map the words to hash values.

* Bloom filter: The BloomFilter class uses the bloom filter algorithm to efficiently determine if a word is a member of a set.

* Search algorithms: The IOSearcher class uses a simple linear search algorithm to scan the files and check if a word exists.


## Advantages

* Some of the advantages of this dicionary implementations:

* Fast: The bloom filter allows for fast querying of the dictionary, as it has a high probability of returning accurate results and does not require scanning all the files.

* Scalable: The bloom filter can handle large amounts of data, as it is designed to be space-efficient and can handle millions of words with a relatively small memory footprint.

* Customizable: The cache manager and bloom filter can be easily customized by changing the size, replacement policy, and hash function. This allows users to tailor the dictionary to their specific needs.

* Extensible: The use of interfaces and design patterns makes it easy to extend the functionality of the dictionary by adding new classes that implement the necessary interfaces.
