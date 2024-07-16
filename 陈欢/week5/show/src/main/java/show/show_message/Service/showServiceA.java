package show.show_message.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import show.show_message.Dao.showDao;
import show.show_message.pojo.User;

import java.util.ArrayList;
@Primary
@Service
public class showServiceA implements showService{
    @Autowired
    private showDao shdao;
    public ArrayList<User> list_users()
    {
        ArrayList<User> users=shdao.listuser();
        return users;
    }
}
