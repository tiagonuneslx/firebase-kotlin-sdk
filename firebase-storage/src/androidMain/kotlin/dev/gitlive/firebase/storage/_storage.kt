package dev.gitlive.firebase.storage

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import kotlinx.coroutines.tasks.await

actual val Firebase.storage: FirebaseStorage get() = FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance())

actual fun Firebase.storage(app: FirebaseApp): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(app.android))

actual fun Firebase.storage(url: String): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(url))

actual fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage =
    FirebaseStorage(com.google.firebase.storage.FirebaseStorage.getInstance(app.android, url))

actual class FirebaseStorage(val android: com.google.firebase.storage.FirebaseStorage) {

    actual fun getReference(): StorageReference = StorageReference(android.reference)

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
//    suspend fun putFile(uri: Uri)
    actual suspend fun getBytes(maxDownloadSizeBytes: Long) =
        android.getBytes(maxDownloadSizeBytes).await()
}