package id.indocyber.common.base

open class AppResponse<T> {
    companion object {
        fun <T> success(value: T): AppResponse<T> =
            AppResponseSuccess(value)

        fun <T> failure(exception: Throwable?): AppResponse<T> =
            AppResponseError(exception)

        fun <T> loading() = AppResponseLoading<T>()
    }

    class AppResponseError<T>(val e: Throwable?) : AppResponse<T>() {

    }

    class AppResponseSuccess<T>(val data: T) : AppResponse<T>() {

    }

    class AppResponseLoading<T>() : AppResponse<T>()
}