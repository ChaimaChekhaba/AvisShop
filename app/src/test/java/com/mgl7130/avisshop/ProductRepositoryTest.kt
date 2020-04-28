package com.mgl7130.avisshop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mgl7130.avisshop.model.Product
import com.mgl7130.avisshop.model.ProductDao
import com.mgl7130.avisshop.viewmodel.ProductRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository : ProductRepository

    @Mock
    private lateinit var productDao: ProductDao

    @Before
    @Throws(Exception::class)
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        productDao = mock()
        repository = ProductRepository(productDao)
    }

    @Test
    @Throws(Exception::class)
    fun test_whenCallInsertProductRepositoryThenCallProductDaoInsert(){

        val p1 = Product("0123456789",
            "product1",
            "image1",
            "0", "0.0","0.0",
            "good points 1",
            "bad points 1",
            "Amazon Link1",
            "Ebay Link1")

        runBlocking{

            repository.insert(p1)
            Mockito.verify(productDao).insert(p1)

        }
    }
}