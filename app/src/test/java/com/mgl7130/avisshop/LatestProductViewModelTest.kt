package com.mgl7130.avisshop

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.viewmodel.LatestProductViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import com.mgl7130.avisshop.viewmodel.ProductRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.internal.util.reflection.FieldSetter


@RunWith(JUnit4::class)
class LatestProductViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var viewModel: LatestProductViewModel

    @Mock
    lateinit var productRepository: ProductRepository

    @Mock
    lateinit var products: LiveData<List<Product>>

    @Before
    @Throws(Exception::class)
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        productRepository = mock()
        products = mock()

        viewModel = LatestProductViewModel(application)
        FieldSetter.setField(viewModel, viewModel.javaClass.getDeclaredField("mProducts"), products)

    }

    @Test
    @Throws(Exception::class)
    fun test_whenProductIsInsertedUpdateListOfProducts(){
        val p1 = Product("0123456789",
            "product1",
            "image1",
            "0", "0.0", "0.0",
            "good points 1",
            "bad points 1",
            "Amazon Link1",
            "Ebay Link1")


        runBlocking {
            Mockito.doAnswer {
                viewModel.mProducts = productRepository.getProducts()
                null

            }.`when`(productRepository).insert(p1)

            productRepository.insert(p1)
        }

        assertEquals(viewModel.mProducts?.getOrAwaitValue(), null)
    }
}
