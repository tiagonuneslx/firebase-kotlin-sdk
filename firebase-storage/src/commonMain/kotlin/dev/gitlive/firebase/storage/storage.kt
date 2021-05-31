package dev.gitlive.firebase.storage

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp

/** Returns the [FirebaseStorage] instance of the default [FirebaseApp]. */
expect val Firebase.storage: FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp]. */
expect fun Firebase.storage(app: FirebaseApp): FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given bucket URL (e.g. gs://my-custom-bucket). */
expect fun Firebase.storage(url: String): FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp] and a given bucket URL (e.g. gs://my-custom-bucket). */
expect fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage

expect class FirebaseStorage {
    val reference: StorageReference
    fun getReferenceFromUrl(fullUrl: String): StorageReference
}

val ONE_MEGABYTE: Long = 1024 * 1024

expect class StorageReference {
    val path: String
    val name: String
    val bucket: String
    fun child(pathString: String): StorageReference
    val parent: StorageReference?
    val root: StorageReference
    suspend fun putBytes(bytes: ByteArray)

    //    suspend fun putStream(stream: InputStream)
    suspend fun putFile(file: File)
    suspend fun putFile(file: File, metadata: StorageMetadata)
    suspend fun getBytes(maxDownloadSizeBytes: Long = ONE_MEGABYTE): ByteArray
    suspend fun getFile(file: File)
}

expect class File

expect class StorageMetadata {
    var contentType: String
        private set

    class Builder() {
        val metadata: StorageMetadata
        var contentType: String
        fun build(): StorageMetadata
    }
}

fun storageMetadata(init: StorageMetadata.Builder.() -> Unit): StorageMetadata =
    StorageMetadata.Builder().run {
        init()
        build()
    }