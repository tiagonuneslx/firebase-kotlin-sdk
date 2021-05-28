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
    fun getReference(): StorageReference
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
//    suspend fun putFile(uri: Uri)
    suspend fun getBytes(maxDownloadSizeBytes: Long = ONE_MEGABYTE): ByteArray
}