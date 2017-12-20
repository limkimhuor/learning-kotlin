package org.coroutines

import java.util.concurrent.CompletableFuture
import kotlin.coroutines.experimental.RestrictsSuspension
import kotlin.coroutines.experimental.SequenceBuilder


import kotlin.coroutines.experimental.buildSequence

//https://github.com/Kotlin/kotlinx.coroutines

/*
/*Asynchronous programming*/
fun requestToken(): Token {
    return token;
}
fun createPost(token: Token, item: Item): Post {
    return post;
}
fun processPost(post: Post) {
    // does some local processing of result
}
fun postItem(item: Item) {
    val token = requestToken()
    val post = createPost(token, item)
    processPost(post)
}

// best practice
suspend fun requestToken(): Token {
    // make request for a token & suspends
    return token // return result when received
}

suspend fun createPost(token: Token, item: Item): Post {
    // sends item to the server & suspends
    return post; // returns result when received
}
fun processPost(post: Post) {
    // does some local processing of result
}

suspend fun postItem(item: Item) {
    // like regular code
    val token = requestToken()          // suspension point
    val post = createPost(token, item)  // suspension point
    processPost(post)
}

// Bonus features
// Regular loop
for((token, item) in list) {
    createPost(token, item)
}
// Regular exception handling
try {
    createPost(token, item)
} catch(e: BadTokenException) {}

// Regular higher-order functions
file.readLines().forEach { line ->
    createPost(token, lin.toItem())
}
// forEach, let, apply, repeat, filter, map, use, etc Every thing like in blocking code

/*Suspending functions*/
// Retrofit async
interface Service {                             // future
    fun createPost(token: Token, item: Item): Call<Post>
}
suspend fun createPost(token: Token, item: Item): Post =
        serviceInstance.createPost(token, item).await() // suspending extension function from integration library
// Composition
val post = createpost(token, item)

// Higher-order functions
val post = retryIO {
    createpost(token, item)
}
// suspending lambda
suspend fun <T> retryIO(block: suspend () -> T): T {
    var curDelay = 1000L // start with 1 sec
    while (true) {
        try {
            return block
        } catch (e.IOException) {
            e.printStackTrace() // log the error
        }
        delay(curDelay)
        curDelay = (curDelay * 2).coerceAtMost(60000L)
    }
}

/*Coroutine builders*/
// revisited
/*A regular function cannot*/
fun postItem(item: Item) { // remove supsend
    // like regular code
    val token = requestToken()          // Error: Suspend function 'requestToken' should be called only from a coroutine or another suspend function
    /*Can suspend execution*/
    val post = createPost(token, item)  // suspension point
    processPost(post)
}
/*-> One cannot simply invoke a suspending function*/

/*Launch coroutine builder*/
fun postItem(item: Item) {
    launch { // returns immeditely, coroutine works in `background thread pool`
        val token = requestToken()
        val post = createPost(token, item)
        processPost(post)
    }
}

/*UI Context*/
fun postItem(item: Item) {
    launch(UI) { // just specify the context
        val token = requestToken()
        val post = createPost(token, item)
        processPost(post) // And it gets executed on UI thread
    }
}
/* what `launch` does */
// a regular function
fun launch(
        context: CoroutineContext = DefaultDispatcher,
        block: suspend () -> Unit // supeding lambda
): Job {}

/* async/await */

// Kotlin way
suspend fun postItem(item: Item) {
    val token = requestToken()
    val post = createPost(token, item)
    processPost(post)
}

// Classic-way
// C#
// mark with asynce
async Task postItem(Item item) {
    val token = await requestToken(); // use await to suspend
    val token = await createPost(token, item);
    processPost(post);
} // return a future `Task`
/* Why no await keyword in Kotlin?
* The problem with async
* C# requestToken() -- concurrent behavior (default)  VALID-> produces Task<Token>
* C# await requestToken() -- sequential behavior VALID-> produces Token
* (*) Kotlin suspending functions are designed to imitate `sequential` behavior by default
* - concurrency is hard and has to be explicit*/

/*Kotlin approach to async*/
// Use-case for async
// C#
async Task<Image> loadImageAsync(String name) {}

var promise1 = loadImageAsync(name1);
var promise2 = loadImageAsync(name2); // Start mulitple operations concurrently

// and await for them
var image1 = await promise1;
var image2 = await promise2;
var result = combineImages(image1, image2);

// Kotlin async function
// regular func                     Kotlin's future type
fun loadImageAsync(name: String): Deferrend<Image> = async { /*Do sth */} // async coroutine builder
var deferrend1 = loadImageAsync(name1)
var deferrend2 = loadImageAsync(name2) // Start mulitple operations concurrently
// and then wait for them
var image1 = deferrend1.await()
var image2 = deferrend2.await() // Suspends until deferred is complete
var result = combineImages(image1, image2)

// Using async function when needed
suspend fun loadImage(name: String): Image {} // is defined as supspending function, not async

suspend fun loadAndCombine(name1: String, name2: String): Image {
    val deferrend1 = async { loadImageAsync(name1) ) }
    val deferrend2 = async { loadImageAsync(name2) ) }
    return combineImages(deferrend1.await(), deferrend2.await())
}

/* Kotlin approach to async
* Kotlin    requesetToken()             sequential behavior(default)     VALID->produces Token
* Kotlin    async { requesetToken() }   concurrent behavior              VALID->produces Defered<Token>
* */

/*Coroutines
* concept: Coroutines are like very light-weight threads*/
/*This coroutine builder runs coroutine in the context of invoker thread*/
fun main(args: Array<String>) = runBlocking<Unit> {
    val jobs = List(100_000) {
        launch {
            delay(1000L) // suspend for 1 second
            print(".")
        }
    }
    jobs.forEach { it.join() } // we can join a job just like a thread
} // Prints 100k dots after ons econd delay

// Try that with 100k threads

fun main(args: Array<String>) {
    val jobs = List(100_000) {
        thread {
            Thread.sleep(1000L)
            print(".")
        }
    }
    jobs.forEach { it.join() }
}
/*-> Exception in thread "main* java.lang.OutOfMemoryError: unable to create new native thread */

/*Java interop*/

/*Java*/
CompletetableFuture<Image> loadImageAsync(String name) {}
CompletetableFuture<Image> loadAndCombineAsync(String name1, String name2) {
    CompletetableFuture<Image> future1 = loadImageAsync(name1);
    CompletetableFuture<Image> future2 = loadImageAsync(name2);
    return future1.thenCompose(image1 ->
    future2.thenCompose(image2 ->
    CompletableFuture.supplyAsync(() ->
    combineImages(image1, image2))));
}

/*Kotlin*/
fun loadAndCombineAsync(
        name1: String,
        name2: String
): CompletableFuture<Image> =
        future { // future coroutine builder
            val future1 = loadImageAsync(name1)
            val future2 = loadImageAsync(name2)
            combineImages(future1.await(), future2.await()) // await() extension for Java's CompletableFuture
        }

/*Beyond asynchronous code*/

/*Fibonacci sequence*/
val fibonacci = kotlin.coroutines.experimental.buildSequence {
    // a coroutine builder with restricted suspension
    var curr = 1
    var next = 1
    while (true) {
        yield(curr) // a suspending function
        val tmp = curr + next
        curr = next
        next = tmp
    }
}

fun <T> buildSequence(  /* Suspending lambda with receiver */
        builderAction: suspend SequenceBuilder<T>.() -> Unit
): Sequence<T> {} // result is a `synchronous` sequence

@RestrictsSuspension abstract class SequenceBuilder<in T> {
    abstract suspend fun yield(value: T)  // Coroutine is restricted only to suspending functions defined here
}

// Synchronous
val fibonacci = kotlin.coroutines.experimental.buildSequence {
    // a coroutine builder with restricted suspension
    var curr = 1
    var next = 1
    while (true) {
        yield(curr) // a suspending function
        val tmp = curr + next
        curr = next
        next = tmp
    }
} // Synchronous with invoker
val iter = fibonacci.iterator()
println(iter.next())  //1
println(iter.next())  //1
println(iter.next())  //2

/*Classic async*/
/*
* Keywords: async/await, generate/yield */

/*Kotlin coroutines*/
/*
* Modifier: suspend
* Standard library: kotlinx-coroutines : launch async, runBlocking, future, delay, Job, Deferred, etc*/

        */
