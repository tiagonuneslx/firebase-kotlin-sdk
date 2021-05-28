package dev.gitlive.firebase.storage

import cocoapods.*

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp

///** Returns the [FirebaseStorage] instance of the default [FirebaseApp]. */
actual val Firebase.storage: FirebaseStorage
    get() = FIRStorage.storage()
//
///** Returns the [FirebaseStorage] instance of a given [FirebaseApp]. */
actual fun Firebase.storage(app: FirebaseApp): FirebaseStorage = FIRStorage.storageForApp(app)
//
///** Returns the [FirebaseStorage] instance of a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(url: String): FirebaseStorage = FIRStorage.storageWithURL(url)

//
///** Returns the [FirebaseStorage] instance of a given [FirebaseApp] and a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage
{
    //TODO
    return FIRStorage.storage()
}
//
actual class FirebaseStorage(val ios: FIRStorage) {
    fun getReference(): StorageReference = StorageReference(ios.reference)

    fun getReferenceFromUrl(fullUrl: String): StorageReference = StorageReference(ios.StorageReference(fullUrl))
}
//
//val ONE_MEGABYTE: Long = 1024 * 1024
//
actual class StorageReference(val ios:FIRStorageReference) {
    val path: String
        get() = ios.fullPath

    val name: String
        get() = ios.name

    val bucket: String
        get() = ios.bucket

    fun child(pathString: String): StorageReference = StorageReference(ios.child(pathString))

    val parent: StorageReference?
    get() = ios.parent()?.let { StorageReference(it) }

    val root: StorageReference
    get() = StorageReference(ios.root())

    suspend fun putBytes(bytes: ByteArray){
        //TODO
    }
//    suspend fun putStream(stream: InputStream)
//    suspend fun putFile(uri: Uri)
    suspend fun getBytes(maxDownloadSizeBytes: Long): ByteArray
    {
        //TODO
        return ByteArray(0)
    }
}