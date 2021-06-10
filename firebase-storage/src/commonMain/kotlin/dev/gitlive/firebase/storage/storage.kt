/*
 * Copyright (c) 2020 GitLive Ltd.  Use of this source code is governed by the Apache 2.0 license.
 */

package dev.gitlive.firebase.storage

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp

/** Returns the [FirebaseStorage] instance of the default [FirebaseApp]. */
expect val Firebase.storage: FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given [url]. */
expect fun Firebase.storage(url: String): FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp]. */
expect fun Firebase.storage(app: FirebaseApp): FirebaseStorage

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp] and [url]. */
expect fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage

expect class FirebaseStorage {
    val app: FirebaseApp
    val maxDownloadRetryTimeMillis: Long
    val maxOperationRetryTimeMillis: Long
    val maxUploadRetryTimeMillis: Long
    val maxUploadRetryTimeMillis: Long
    val reference: StorageReference

    fun getReference(location: String): StorageReference
    fun getReferenceFromUrl(fullUrl: String): StorageReference
    fun useEmulator(host: String, port: Int)
}

expect class StorageReference {
    val storage: FirebaseStorage
    val bucket: String
    val name: String
    val parent: StorageReference?
    val path: String
    val root: StorageReference
//    val activeDownloadTasks: List<FileDownloadTask>
//    val activeUploadTasks: List<UploadTask>

    fun child(pathString: String): StorageReference
    suspend fun delete(pathString: String)
    suspend fun getBytes(maxDownloadSizeBytes: Long): ByteArray
    suspend fun putBytes(bytes: ByteArray)
    suspend fun getFile(destinationUri: Uri)
    suspend fun getDownloadUrl(): Uri
    suspend fun getMetadata(): StorageMetadata
    suspend fun putBytes(bytes: ByteArray, metadata : StorageMetadata)
    suspend fun putFile(file: Uri)
    suspend fun putFile(file: Uri, metadata: StorageMetadata)
    suspend fun putFile(file: Uri, metadata: StorageMetadata, existingUploadUri: Uri)
    suspend fun updateMetadata(metadata: StorageMetadata)
    suspend fun list(maxResults: Int): ListResult
    suspend fun list(maxResults: Int, pageToken: String): ListResult
    suspend fun listAll(maxResults: Int, pageToken: String): ListResult
//    suspend fun getStream(): InputStream
//    suspend fun getStream(processor: StreamDownloadTask.StreamProcessor): InputStream
//    suspend fun getStream(processor: StreamDownloadTask.StreamProcessor): InputStream
//    suspend fun putStream(stream: InputStream)
//    suspend fun putStream(stream: InputStream, metadata: StorageMetadata)
}

expect class StorageMetadata {
    val bucket: String?
    val cacheControl: String?
    val reference: StorageReference?
    val contentEncoding: String?
    val contentLanguage: String?
    val contentType: String?
    val creationTimeMillis: Long
    val customMetadataKeys: Set<String>
    val contentDisposition: String?
    val generation: String?
    val md5Hash: String?
    val metadataGeneration: String?
    val name: String?
    val path: String?
    val sizeBytes: Long
    val updatedTimeMillis: Long

    fun getCustomMetadata(key: String)
}

expect class ListResult {
    val items: List<StorageReference>
    val pageToken: String?
    val prefixes: List<StorageReference>
}

expect class Uri