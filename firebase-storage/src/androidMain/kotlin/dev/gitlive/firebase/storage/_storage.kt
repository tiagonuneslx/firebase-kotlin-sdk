package dev.gitlive.firebase.storage

import android.net.Uri
import com.google.firebase.storage.FileDownloadTask
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual val Firebase.storage: FirebaseStorage get() = FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance())

actual fun Firebase.storage(app: FirebaseApp): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(app.android))

actual fun Firebase.storage(url: String): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(url))

actual fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(app.android, url))

actual class FirebaseStorage(val android: com.google.firebase.storage.FirebaseStorage) {

    actual val reference: StorageReference get() = StorageReference(android.reference)

    actual fun getReferenceFromUrl(fullUrl: String): StorageReference =
        StorageReference(android.getReferenceFromUrl(fullUrl))
}

actual class StorageReference(val android: com.google.firebase.storage.StorageReference) {
    actual val path: String get() = android.path
    actual val name: String get() = android.name
    actual val bucket: String get() = android.bucket
    actual fun child(pathString: String): StorageReference =
        StorageReference(android.child(pathString))

    actual val parent: StorageReference? get() = android.parent?.let { StorageReference(it) }
    actual val root: StorageReference get() = StorageReference(android.root)
    actual suspend fun putBytes(bytes: ByteArray) {
        android.putBytes(bytes).await()
    }

    //    suspend fun putStream(stream: InputStream)
    actual suspend fun putFile(file: File) {
        suspendCancellableCoroutine<Unit> { continuation ->
            android.putFile(file.uri).addOnCompleteListener {
                continuation.resume(Unit)
            }
        }
    }

    actual suspend fun putFile(file: File, metadata: StorageMetadata) {
        suspendCancellableCoroutine<Unit> { continuation ->
            android.putFile(file.uri, metadata.android).addOnCompleteListener {
                continuation.resume(Unit)
            }
        }
    }

    actual suspend fun getBytes(maxDownloadSizeBytes: Long) =
        android.getBytes(maxDownloadSizeBytes).await()

    actual suspend fun getFile(file: File) {
        suspendCancellableCoroutine<FileDownloadTask.TaskSnapshot> { continuation ->
            android.getFile(file.uri)
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }
    }
}

actual class File(val uri: Uri) {
    constructor(file: java.io.File) : this(Uri.fromFile(file))
}

actual class StorageMetadata(val android: com.google.firebase.storage.StorageMetadata) {
    actual var contentType: String = ""

    actual class Builder {
        actual val metadata: StorageMetadata =
            StorageMetadata(com.google.firebase.storage.StorageMetadata())

        actual var contentType: String by metadata::contentType

        actual fun build(): StorageMetadata = metadata
    }
}