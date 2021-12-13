import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

interface DataSource

class DataBaseDataSource : DataSource

class NetworkDataSource : DataSource

// This class defines an input point for creation
// This class is the one that manages who creates, but not creation itself
//This class only knows about Factories and return the created object
abstract class DataSourceFactory {

    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
            when (T::class) {
                DataBaseDataSource::class -> DataBaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class DataBaseFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = DataBaseDataSource()
}

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()

}

class AbstractFactoryTest {
    @Test
    fun abstractFactoryTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DataBaseDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()
        println("Created data source: $dataSource")
        Assertions.assertThat(dataSource).isInstanceOf(DataBaseDataSource::class.java)
    }
}