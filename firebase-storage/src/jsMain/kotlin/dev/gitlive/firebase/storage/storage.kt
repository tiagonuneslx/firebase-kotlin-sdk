import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp

/** Returns the [FirebaseStorage] instance of the default [FirebaseApp]. */
actual val Firebase.storage: FirebaseStorage get() = TODO("Not yet implemented")

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp]. */
actual fun Firebase.storage(app: FirebaseApp): FirebaseStorage = TODO("Not yet implemented")

/** Returns the [FirebaseStorage] instance of a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(url: String): FirebaseStorage = TODO("Not yet implemented")

/** Returns the [FirebaseStorage] instance of a given [FirebaseApp] and a given bucket URL (e.g. gs://my-custom-bucket). */
actual fun Firebase.storage(app: FirebaseApp, url: String): FirebaseStorage = TODO("Not yet implemented")

actual class FirebaseStorage {
    actual fun getReference(): StorageReference {
        TODO("Not yet implemented")
    }

    actual fun getReferenceFromUrl(fullUrl: String): StorageReference {
        TODO("Not yet implemented")
    }
}

actual class StorageReference {
    actual val path: String
        get() = TODO("Not yet implemented")
    actual val name: String
        get() = TODO("Not yet implemented")
    actual val bucket: String
        get() = TODO("Not yet implemented")

    actual fun child(pathString: String): StorageReference {
        TODO("Not yet implemented")
    }

    actual val parent: StorageReference?
        get() = TODO("Not yet implemented")
    actual val root: StorageReference
        get() = TODO("Not yet implemented")

    actual suspend fun putBytes(bytes: ByteArray) {
    }

    actual suspend fun getBytes(maxDownloadSizeBytes: Long): ByteArray {
        TODO("Not yet implemented")
    }
}