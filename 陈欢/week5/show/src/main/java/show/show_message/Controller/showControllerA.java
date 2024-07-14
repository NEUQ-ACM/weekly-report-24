package show.show_message.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import show.show_message.Response;
import show.show_message.Service.showService;
import show.show_message.pojo.User;

import java.util.ArrayList;

@RestController
public class showControllerA {
    @Autowired
    private showService shse;
    @RequestMapping("/show message")
    public Response show() {
        ArrayList<User> users=shse.list_users();
        return Response.success(users.toString());
    }
}
