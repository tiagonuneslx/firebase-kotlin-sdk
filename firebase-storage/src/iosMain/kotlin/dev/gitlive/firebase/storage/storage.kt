package dev.gitlive.firebase.storage

import cocoapods.FirebaseStorage.*
import platform.Foundation.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

///** Returns the [FirebaseStorage] instance of the default [FirebaseApp]. */
actual val Firebase.storage: FirebaseStorage
    get() = FirebaseStorage(FIRStorage.storage())

//
///** Returns the [FirebaseStorage] instance of a given [FirebaseApp]. */
actual fun Firebase.storage(app: FirebaseApp): FirebaseStorage =
    FirebaseStorage(FIRStorage.storageForApp(app.ios))

//
///** Returns the [FirebaseStorage] instance of a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(url: String): FirebaseStorage =
    FirebaseStorage(FIRStorage.storageWithURL(url))

//
///** Returns the [FirebaseStorage] instance of a given [FirebaseApp] and a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage {
    //TODO
    return storage
}

//
actual class FirebaseStorage(val ios: FIRStorage) {
    actual val reference: StorageReference
        get() = StorageReference(ios.reference())

    actual fun getReferenceFromUrl(fullUrl: String): StorageReference =
        StorageReference(ios.referenceForURL(fullUrl))
}

//
//val ONE_MEGABYTE: Long = 1024 * 1024
//
actual class StorageReference(val ios: FIRStorageReference) {
    actual val path: String
        get() = ios.fullPath

    actual val name: String
        get() = ios.name

    actual val bucket: String
        get() = ios.bucket

    actual fun child(pathString: String): StorageReference = StorageReference(ios.child(pathString))

    actual val parent: StorageReference?
        get() = ios.parent()?.let { StorageReference(it) }

    actual val root: StorageReference
        get() = StorageReference(ios.root())

    actual suspend fun putBytes(bytes: ByteArray) {
        //TODO
    }

    //    suspend fun putStream(stream: InputStream)
//    suspend fun putFile(uri: Uri)
    actual suspend fun getBytes(maxDownloadSizeBytes: Long): ByteArray {
        //TODO
        return ByteArray(0)
    }

    actual suspend fun putFile(file: File) {

        return suspendCancellableCoroutine { cont ->
            val task: FIRStorageUploadTask = ios.putFile(file.file, null) { meta, error ->
                cont.resume(Unit)
            }
        }
    }

    actual suspend fun putFile(file: File, metadata: StorageMetadata) {

        return suspendCancellableCoroutine { cont ->
            val task: FIRStorageUploadTask = ios.putFile(file.file, metadata.ios) { meta, error ->
                cont.resume(Unit)
            }
        }
    }

    actual suspend fun getFile(file: File) {
        ios.writeToFile(file.file)
    }


}

actual class File(val file: NSURL)

actual class StorageMetadata(val ios: FIRStorageMetadata) {
    actual var contentType: String = ""

    actual class Builder {
        actual val metadata: StorageMetadata =
            StorageMetadata(FIRStorageMetadata())

        actual var contentType: String by metadata::contentType

        actual fun build(): StorageMetadata = metadata
    }
}
