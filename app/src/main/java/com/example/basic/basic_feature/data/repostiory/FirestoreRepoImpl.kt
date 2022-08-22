package com.example.basic.basic_feature.data.repostiory

import android.util.Log
import com.example.basic.basic_feature.data.local.dao.BasicDao
import com.example.basic.basic_feature.data.local.enitity.CollectionEntity
import com.example.basic.basic_feature.data.local.enitity.FBEntity
import com.example.basic.basic_feature.data.local.enitity.HZEntity
import com.example.basic.basic_feature.data.remote.dto.ProductModelDto
import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUseCase
import com.example.basic.basic_feature.data.remote.firebase.repo.FirestoreRepo
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirestoreRepoImpl(
    private val dao: BasicDao,
    private val useCase: FirebaseUseCase,
): FirestoreRepo {

    override fun getAllHZProduct(collection: String): Flow<Resource<List<ProductModel>>> = flow {

        try {

            val productList = mutableListOf<HZEntity>()

            emit(Resource.Loading(data = emptyList<ProductModel>()))

            val hzEntity = dao.selectAllHz().map {
                it.toProductModel()
            }

            emit(Resource.Loading(data = hzEntity))

            val query = useCase.getProductModelDto(collection = collection)

            if (query != null) {
                for (document in query.documents) {
                    document.toObject(ProductModelDto::class.java)?.toHZEntity()?.let {
                        productList.add(it)
                    }
                }

                dao.deleteHzList(productList.map {
                    it.productName.toString()
                })

                dao.insertHzList(productList)

                val newHzList = dao.selectAllHz().map {
                    it.toProductModel()
                }
                emit(Resource.Success(data = newHzList))
            }

        }catch (e: Exception) {
            dao.deleteAllHzList()
            Log.i("hz", "error happened ${e.message}")
            emit(Resource.Error(data = emptyList<ProductModel>(),message = e.message))
        }
    }

    override fun getAllFBProduct(collection: String): Flow<Resource<List<ProductModel>>> = flow {
        val productList = mutableListOf<FBEntity>()

        emit(Resource.Loading())
        val fbEntity = dao.selectAllFb().map {
            it.toProductModel()
        }

        emit(Resource.Loading(data = fbEntity))

        try {
            val query = useCase.getProductModelDto(collection = collection)
            if (query != null) {
                for (document in query.documents) {
                    document.toObject(ProductModelDto::class.java)?.toFBEntity()?.let {
                        productList.add(it)
                    }
                }
                dao.deleteFbList(productList.map {
                    it.productName.toString()
                })

                dao.insertFbList(productList)

                val newHzList = dao.selectAllFb().map {
                    it.toProductModel()
                }
                emit(Resource.Success(data = newHzList))
            }
        }catch (e: Exception) {
            dao.deleteAllFbList()
            Log.i("fb", "error happened ${e.message}")
            emit(Resource.Error(data = fbEntity, message = e.message))
        }
    }

    override fun getAllCollectionProduct(collection: String): Flow<Resource<List<ProductModel>>> = flow {
        val collectionList = mutableListOf<CollectionEntity>()

        emit(Resource.Loading())

        val collectionEntity = dao.selectAllCollection(collection = collection).map {
            it.toProductModel()
        }

        emit(Resource.Loading(data = collectionEntity))

        try {
            val query = useCase.getProductModelDto(collection = collection)
            if (query != null) {
                for (document in query.documents) {
                    document.toObject(ProductModelDto::class.java)?.toCollectionEntity(collection = collection)?.let {
                        collectionList.add(it)
                    }
                }

                dao.deleteCollectionList(collectionList.map {
                    it.productName.toString()
                }, collection = collection)

                dao.insertCollectionList(collectionList)

                val newCollectionList = dao.selectAllCollection(collection = collection).map {
                    it.toProductModel()
                }

                emit(Resource.Success(data = newCollectionList))
            }
        }catch (e: Exception) {
            dao.deleteAllCollectionList(collection = collection)
            Log.i("fb", "error happened ${e.message}")
            emit(Resource.Error(data = collectionEntity, message = e.message))
        }
    }

}