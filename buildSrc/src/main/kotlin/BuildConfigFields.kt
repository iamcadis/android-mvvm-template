import java.io.File

object BuildConfigFields {

    private const val MAJOR = 1
    private const val MINOR = 0
    private const val PATCH = 0

    // SDK Version
    const val COMPILE = 30
    const val MINIMAL = 21
    const val TARGET = 30

    // Url for WebServices
    const val URL_DEVELOPMENT = "\"https://dev.app.justgo.com.sg/api/\""
    const val URL_PRODUCTION = "\"https://dev.app.justgo.com.sg/api/\""

    // Android Version
    const val VERSION_CODE = MAJOR * 10000 + MINOR * 1000 + PATCH * 100
    const val VERSION_NAME = "$MAJOR.$MINOR.$PATCH"

    fun getAndroidKeystoreFile(androidKeystorePath: String?): File? {
        return if (androidKeystorePath == null || androidKeystorePath.isEmpty()) {
            null
        } else {
            File(androidKeystorePath)
        }
    }

}