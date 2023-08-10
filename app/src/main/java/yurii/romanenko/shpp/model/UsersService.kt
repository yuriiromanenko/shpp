package yurii.romanenko.shpp.model

import com.github.javafaker.Faker
import java.util.Collections

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {

    private var users = mutableListOf<User>()

    private val listeners = mutableListOf<UsersListener>()


    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                photo = IMAGES[it % IMAGES.size]
            ) }.toMutableList()
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(user: User) {
        val indexToDelete: Int = users.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) {
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveUser(user: User, moveBy: Int) {
        val oldIndex: Int = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return
        val newIndex: Int = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach {it.invoke(users)}
    }


    companion object {
        private val IMAGES = mutableListOf(
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/279453484_115113707847657_3198879854291464462_n.jpg?stp=c17.0.240.240a_dst-jpg_p240x240&_nc_cat=102&ccb=1-7&_nc_sid=7206a8&_nc_ohc=kZPJGdrfhjwAX8dl17G&_nc_ht=scontent-iev1-1.xx&oh=00_AfABGOsfkuD2uiPARMXIhZ-_PrD2MZlUvbMxNehU7Bgqxw&oe=64D51298",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t31.18172-1/11013265_782082698527846_4874811237871607940_o.jpg?stp=dst-jpg_p240x240&_nc_cat=109&ccb=1-7&_nc_sid=7206a8&_nc_ohc=sQ0i8VY4VA4AX-bXeZO&_nc_ht=scontent-iev1-1.xx&oh=00_AfCeA-VogVgFFk4CzxiKc3fBCp5kIejO8xquGG1HE42cUg&oe=64F87B17",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/327054870_3384058871867477_1143574486730716293_n.jpg?stp=c0.19.240.240a_dst-jpg_p240x240&_nc_cat=108&ccb=1-7&_nc_sid=7206a8&_nc_ohc=om7HS6JZ7W0AX9VYN0E&_nc_ht=scontent-iev1-1.xx&oh=00_AfBKkTE62z0btI7W3dWsEDI7h3Yg1YrjnmdivOReLLbRKg&oe=64D69195",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/271544487_1425659271190985_3841168621686753476_n.jpg?stp=dst-jpg_p240x240&_nc_cat=109&ccb=1-7&_nc_sid=7206a8&_nc_ohc=E9m56yvBsqsAX8DQRUy&_nc_ht=scontent-iev1-1.xx&oh=00_AfBpPJZsTOSkmIQ2V_6qBOWDnwiSBJD2bC8mMuTnLzTftA&oe=64D56E51",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/240668443_2190090624476841_2078300820407787918_n.jpg?stp=dst-jpg_p240x240&_nc_cat=108&ccb=1-7&_nc_sid=7206a8&_nc_ohc=ltuTi6FEhecAX9FI51m&_nc_ht=scontent-iev1-1.xx&oh=00_AfCus-B16_m3X6eYItyt3f-JYNBmX4r9JctHr85lsGIKfw&oe=64D5135C",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/277720580_522819039376930_6763511257190350103_n.jpg?stp=dst-jpg_p240x240&_nc_cat=105&ccb=1-7&_nc_sid=7206a8&_nc_ohc=PONJl8gskwsAX-OhlxW&_nc_ht=scontent-iev1-1.xx&oh=00_AfAlglMNm5oPlY36dJbATUEhaiC1p8XM-3MvgAi_SuPeSg&oe=64D645F9",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t1.6435-1/95096996_2963349480418775_4447837689009405952_n.jpg?stp=dst-jpg_p240x240&_nc_cat=101&ccb=1-7&_nc_sid=7206a8&_nc_ohc=inw-yzwti-wAX-mppbO&_nc_ht=scontent-iev1-1.xx&oh=00_AfB_dK_8UcYzd8JVnZPXLRvjO2yYh3ZPojkLfKSzgtsR6A&oe=64F89D49",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/339016752_161307880180629_7933378763009234344_n.jpg?stp=dst-jpg_p240x240&_nc_cat=103&ccb=1-7&_nc_sid=7206a8&_nc_ohc=Fy2eXVNtX50AX9FdYZ-&_nc_ht=scontent-iev1-1.xx&oh=00_AfCRQSyxTsQnUUcBgWpjC93zpQWoOwqw20NCQw2JHLcLvw&oe=64D5543F",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/309911292_3312447389025378_6041443700093127257_n.jpg?stp=c426.727.1016.1016a_dst-jpg_s240x240&_nc_cat=103&ccb=1-7&_nc_sid=7206a8&_nc_ohc=iXSOFy2LrqQAX-YCTwg&_nc_ht=scontent-iev1-1.xx&oh=00_AfDXD-xlZ828fcbu3TAxvnFfAGfqCXoAR8CYBnTCetnf1w&oe=64D6733F",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/352159970_963186714880100_8848162823613444294_n.jpg?stp=dst-jpg_p240x240&_nc_cat=100&ccb=1-7&_nc_sid=7206a8&_nc_ohc=ethx5pHoOWcAX93qo-t&_nc_ht=scontent-iev1-1.xx&oh=00_AfDICbJzXttkfjARiYeVjJjNMmzb5_dh-Qzaxl-E0obzAw&oe=64D6617F",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t39.30808-1/350328583_656385799677558_8296565115621786601_n.jpg?stp=dst-jpg_p240x240&_nc_cat=110&ccb=1-7&_nc_sid=7206a8&_nc_ohc=MNlhZQaPok4AX9dGOoi&_nc_ht=scontent-iev1-1.xx&oh=00_AfChpnd2vipOLNz4czX8lBmz2UYBkXpJ8QmjMXkDSECSlg&oe=64D52548",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t1.6435-1/199406672_10220359032745742_7869467853796882441_n.jpg?stp=dst-jpg_p240x240&_nc_cat=106&ccb=1-7&_nc_sid=7206a8&_nc_ohc=hHrbbAcj_OQAX9uaKlW&_nc_ht=scontent-iev1-1.xx&oh=00_AfCmCkw7HhqzVCyic7_8aNKRz54k02rM_knUJNpd89gBqQ&oe=64F87D3D",
            "https://scontent-iev1-1.xx.fbcdn.net/v/t31.18172-1/11934559_845050112278180_9215609394986458328_o.jpg?stp=dst-jpg_p240x240&_nc_cat=100&ccb=1-7&_nc_sid=7206a8&_nc_ohc=QNu1Lvvm9bUAX9Hhrey&_nc_ht=scontent-iev1-1.xx&oh=00_AfDJRYYc90azdo3iQ0j8I9UoPXI2j3iCh29XMmYkwn-8bA&oe=64F88706"
        )
    }

}