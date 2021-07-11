# CodingameFramework

This project contains some __Java__ utilities/tips for __[Codingame](https://www.codingame.com/multiplayer)__.  
  
When i started on Codingame, i tried to reach legend in a lot of multis. It seems like an opportunity to practice java 8. My bots were mostly heuristic with no use of a timer. 
But then, after pushing back as much as possible, i had to do [UTTT](https://www.codingame.com/multiplayer/bot-programming/tic-tac-toe). It changed my vision of Codingame and now i'm redoing all my previous bots.  
  
Here some Java tips that i've learned so far :

* Use a [Merger](CodingameFramework/src/main/java/fr/framework/merger/ClassMerger.java) that allows you to __code in multiple files__. I coded the [CodinGame Sponsored Challenge](https://www.codingame.com/multiplayer/optimization/codingame-sponsored-contest) in one file. I had to stop when it wasn't readable anymore (still got to redo it).

* Use a __personal [Logger](CodingameFramework/src/main/java/fr/framework/logger/Logger.java)__ printing time elapsed and class name.

* Go back to the source. __Forget about Java 8__ (Stream, Optional...). I even removed foreach loop in UTTT.

* I use mostly __primitives and arrays__ rather than objects. In this project to store a position, i used a byte rather than an object with x and y fields (see [PointUtils](CodingameFramework/src/main/java/fr/framework/PointUtils.java)).

* I almost __don’t use List, Set, Map__... anymore. I have small [utilities](CodingameFramework/src/main/java/fr/framework/list/ByteListUtils.java) to use arrays instead of collections (basically the first element of the array is the number of elements in the "list"). It avoids boxing/unboxing, the creation of "useless" objects (Integer, Byte...), to clear a "list" you only set the first element to 0... Look at [PlaceUtils](CodingameFramework/src/main/java/code/utils/place/PlaceUtils.java) to see a use case example.

* I use __[assertions](CodingameFramework/src/main/java/fr/framework/AssertUtils.java)__ rather than Unit Tests to make sure that there's no bugs/regressions.  
For example in [Spring Challenge 2021](https://www.codingame.com/multiplayer/bot-programming/spring-challenge-2021) : i kept the state of the game at the beginning of my last turn, then i "guessed" the opponent actions, used my simulation to play my action and his, and compare (with assertions) the result and the game inputs.

* Create __caches__ ([around positions](CodingameFramework/src/main/java/fr/framework/MapUtils.java), distances...)
 
* One of the main problem using Java in CodingGame (beside its slowness) is garbage collector timeouts. One way to reduce them is to implement the trick in [GarbageCollectorUtils](CodingameFramework/src/main/java/fr/framework/timeout/GarbageCollectorUtils.java) (see also [JVM memory issues](https://www.codingame.com/forum/t/java-jvm-memory-issues/1494)). But mostly you must __avoid creating garbage collectable objects__. For an example, you can look at the [Beam Search](CodingameFramework/src/main/java/code/utils/search/BeamWaySearcher.java) implementation.

I'm also beginning to use bitboards (but no java specific tips).  
  
There an [associated post](https://www.codingame.com/forum) in the Codingame forum.  
Hope it will be helpful.


