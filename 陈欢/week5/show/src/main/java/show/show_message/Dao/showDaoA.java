package show.show_message.Dao;

import org.springframework.stereotype.Repository;
import show.show_message.pojo.Address;
import show.show_message.pojo.User;

import java.util.ArrayList;
import java.util.Scanner;

@Repository
public class showDaoA implements showDao{
    //创建Scanner对象
    Scanner read = new Scanner(System.in);

    @Override
    public ArrayList<User> listuser() {
        ArrayList<User> users = new ArrayList<>();
        int n=read.nextInt();
        /* 5
ch 18 china qhd
cl 5 china ww
tdb 18 wc zj
yss 18 canada dotkn
hzy 50 china 123
           * */
        System.out.println("请输入"+n+"位员工的姓名、年龄、居住地");
        for(int i=0;i<n;i++){

            User new_user = new User();
            new_user.setName(read.next());
            new_user.setAge(read.nextInt());

            String country = read.next();
            String city = read.next();
            Address addr = new Address();
            addr.setCity(city);
            addr.setCountry(country);

            new_user.setAddress(addr);
            users.add(new_user);
        }
        return users;
    }
}
