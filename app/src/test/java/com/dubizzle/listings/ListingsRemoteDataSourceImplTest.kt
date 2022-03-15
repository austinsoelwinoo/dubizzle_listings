package com.dubizzle.listings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dubizzle.listings.core.domain.Listing
import com.dubizzle.listings.framework.ListingsRemoteDataSourceImpl
import com.dubizzle.listings.framework.api.ListingApiResponse
import com.dubizzle.listings.framework.api.ListingsApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class ListingsRemoteDataSourceImplTest {
    private lateinit var networkRepository: ListingsRemoteDataSourceImpl

    @MockK
    private lateinit var service: ListingsApi

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK private lateinit var readAllResponse: Response<ListingApiResponse>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        networkRepository = ListingsRemoteDataSourceImpl(service)
    }

    @Test
    fun `should return empty list by default`() {
        every { readAllResponse.body() } returns null
        every { readAllResponse.isSuccessful } returns true
        coEvery { service.getBooks() } returns readAllResponse

        runBlocking {
            val movies = networkRepository.readAll()
            assert(movies.isEmpty())
        }
    }

    @Test
    fun `should return list with some items for successful with data`() {
        every { readAllResponse.body() } returns ListingApiResponse(results = listOf(Listing("", "","Bike","", arrayListOf(), arrayListOf(), arrayListOf()), Listing("", "","Notebook","", arrayListOf(), arrayListOf(), arrayListOf())))
        every { readAllResponse.isSuccessful } returns true
        coEvery { service.getBooks() } returns readAllResponse

        runBlocking {
            val movies = networkRepository.readAll()
            assert(movies.size == 2)
        }
    }

    @Test
    fun `should return empty list for failed response`() {
        every { readAllResponse.body() } returns null
        every { readAllResponse.isSuccessful } returns false
        coEvery { service.getBooks() } returns readAllResponse

        runBlocking {
            val movies = networkRepository.readAll()
            assert(movies.isEmpty())
        }
    }

    @Test
    fun `should return empty list for exception`() {
        every { readAllResponse.body() } returns null
        every { readAllResponse.isSuccessful } returns false
        coEvery { service.getBooks() } throws Exception()

        runBlocking {
            val movies = networkRepository.readAll()
            assert(movies.isEmpty())
        }
    }
}