package com.example.basic.basic_feature.di


import android.app.Application
import androidx.room.Index
import androidx.room.Room
import com.example.basic.basic_feature.data.local.BasicDatabase
import com.example.basic.basic_feature.data.local.Converters
import com.example.basic.basic_feature.data.local.dao.BasicDao
import com.example.basic.basic_feature.data.local.dao.CartDao
import com.example.basic.basic_feature.data.local.dao.OrderDao
import com.example.basic.basic_feature.data.remote.firebase.repo.FirebaseUser
import com.example.basic.basic_feature.data.remote.firebase.repo.FirestoreRepo
import com.example.basic.basic_feature.data.repostiory.*
import com.example.basic.basic_feature.data.util.GsonParser
import com.example.basic.basic_feature.domain.repository.CartRepository
import com.example.basic.basic_feature.domain.repository.OrderRepository
import com.example.basic.basic_feature.domain.use_case.*
import com.example.basic.core.utils.Constants.BASIC_DATABASE
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class BasicModule {

    @Provides
    @Singleton
    fun provideGetProduct(repo: FirestoreRepo): GetProduct {
        return GetProduct(
            repository = repo
        )
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideFirebaseUser(context: CoroutineContext): FirebaseUser = FirebaseUserImpl(context)

    @Provides
    @Singleton
    fun provideRepository(
        dao: BasicDao): FirestoreRepo {
        return FirestoreRepoImpl(
            dao,
            FirebaseUseCaseImpl()
        )
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        dao: OrderDao
    ): OrderRepository {
        return OrderRepositoryImplementation(dao = dao)
    }

    @Provides
    @Singleton
    fun provideDao(basicDb: BasicDatabase): BasicDao {
        return basicDb.dao
    }

    @Provides
    @Singleton
    fun provideCartDao(basicDb: BasicDatabase): CartDao {
        return basicDb.cartDao
    }

    @Provides
    @Singleton
    fun provideOrderDao(basicDb: BasicDatabase): OrderDao {
        return basicDb.orderDao
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): BasicDatabase {
        return Room.databaseBuilder(
            application,
            BasicDatabase::class.java,
            BASIC_DATABASE)
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideFavouriteRepository(basicDao: BasicDao): FavouriteRepository {
        return FavouriteRepository(basicDao = basicDao)
    }

    @Provides
    @Singleton
    fun provideFavouriteUseCase(favouriteRepository: FavouriteRepository): GetFavourite {
        return GetFavourite(favouriteRepository = favouriteRepository)
    }

    @Provides
    @Singleton
    fun provideInsertFavourite(favouriteRepository: FavouriteRepository): InsertFavourite {
        return InsertFavourite(favouriteRepository = favouriteRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveFavourite(favouriteRepository: FavouriteRepository): RemoveFavourite {
        return RemoveFavourite(favouriteRepository = favouriteRepository)
    }

    @Provides
    @Singleton
    fun provideIsFavourite(favouriteRepository: FavouriteRepository): IsFavourite {
        return IsFavourite(favouriteRepository = favouriteRepository)
    }

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImplementation(cartDao)
    }

    @Provides
    @Singleton
    fun provideDeleteCartItemUseCase(cartRepository: CartRepository): DeleteCartItemUseCase {
        return DeleteCartItemUseCase(repository = cartRepository)
    }

    @Provides
    @Singleton
    fun provideInsertCartItemUseCases(cartRepository: CartRepository): InsertCartItemUseCase {
        return InsertCartItemUseCase(repository = cartRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllCartItemUseCase(cartRepository: CartRepository): GetAllCartItemsUseCase {
        return GetAllCartItemsUseCase(repository = cartRepository)
    }

    @Provides
    @Singleton
    fun provideInsertOrderUseCase(
        repository: OrderRepository
    ): InsertOrderUseCase {
        return InsertOrderUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun getAllOrderUseCase(
        repository: OrderRepository
    ): GetAllOrdersUseCase {
        return GetAllOrdersUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun providePhotoUpdate(
        firebaseUser: FirebaseUser
    ): UpdateProfilePicture {
        return UpdateProfilePicture(firebaseUser = firebaseUser)
    }

    @Provides
    @Singleton
    fun provideAddUsernameUseCase(
        firebaseUser: FirebaseUser
    ): AddUsernameUseCase {
        return AddUsernameUseCase(firebaseUser = firebaseUser)
    }
}

