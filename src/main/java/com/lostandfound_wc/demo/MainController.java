package com.lostandfound_wc.demo;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import sun.net.www.protocol.http.AuthenticationInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    UserRepository userRepository;


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String showIndex(Model model){
        model.addAttribute("items",itemRepository.findAllByItemCategoryContainingIgnoreCase("Lost"));
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String showAddItemForm(Model model){
        model.addAttribute("item",new Item());
        return"/add";
    }








    @RequestMapping("userlist")
    public String showUserList(Model model, Authentication auth)
    {
        if(auth!=null)
        {
            User thisUser = userRepository.findUserByUsername(auth.getName());
            if(thisUser!=null){
                model.addAttribute("useritems", thisUser.getMyItems());
                System.out.println(auth.getName() + "authorities:" +auth.getAuthorities().toString());
                model.addAttribute("currentuser", userRepository.findUserByUsername(auth.getName()));
            }
        }

        else
        {
            if(auth!=null)
                model.addAttribute("inMemory",true);
            else
                model.addAttribute("inMemory",false);
        }

        return "index";
    }




    @GetMapping("/additem")
    public String addItem(Model model){
        model.addAttribute("itemobject",new Item());
        return"additem";
    }



    @PostMapping("/additem")
    public String saveMyItem(@Valid @ModelAttribute("itemobject") Item item, BindingResult result, Authentication auth)
    {
        if (result.hasErrors()) {
            return "additem";
        }
        itemRepository.save(item);
        User currentUser = userRepository.findUserByUsername(auth.getName());
        currentUser.getMyItems().toString();
        currentUser.addItem(item);
        userRepository.save(currentUser);

        return "redirect:/";

    }


    @GetMapping("/listitems")
    public @ResponseBody String listItems(Authentication auth)
    {
        return userRepository.findUserByUsername(auth.getName()).getMyItems().toString();
    }

//    @GetMapping("/additemtouser/{id}")
//    public String addItemToUser(Model model, @PathVariable("id") long id)
//    {
//        //model.addAttribute("currentUser", userRepository.findAll());
//        model.addAttribute("itemobject",itemRepository.findOne(id));
//        return"userlist";
//    }





//    @PostMapping("/additemtouser")
//    public String showItemToUser(HttpServletRequest request, Model model)
//    {
//        String userid = request.getParameter("userid");
//        model.addAttribute("newuseritem",userRepository.findOne(new Long(userid)));
//
//        //Make items disappear from add form when they are already included (Set already makes it impossible to add multiple)
//        //model.addAttribute("skillList",skillRepository.findAll());
//
//        return "addskilltojob";
//    }













    @PostMapping("/process")
    public String processItem(@Valid @ModelAttribute("item") Item item,BindingResult result){

        if (result.hasErrors()) {
            return "itemform";
        }
        itemRepository.save(item);
        return "redirect:/list";
    }


    @RequestMapping("/list")
    public String listItems(Model model){
        model.addAttribute("items",itemRepository.findAllByFoundContainingIgnoreCase("No"));
        return"list";
    }

    @RequestMapping("/currentlist")
    public String currentListings(Model model){
        model.addAttribute("items",itemRepository.findAll());
        return"currentlist";
    }


    @RequestMapping("/userlist")
    public String usersListings(Model model){
        model.addAttribute("items",itemRepository.findAll());
        return"userlist";
    }
    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable("id")long id, Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        return "showitemdetails";
    }

    @RequestMapping("/update/{id}")
    public String updateDetail(@PathVariable("id")long id,Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        return "add";
    }

    @RequestMapping("/found/{id}")
    public String foundItem(@PathVariable("id") long id,Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("Yes");

        //String roomRentMessage="\""+room.getAddress()+"\""+" is rented";

        //redirectAttributes.addFlashAttribute("message1", roomRentMessage);

        model.addAttribute("anItem", itemRepository.findOne(id));
        itemRepository.save(item);
        return "redirect:/list";
    }
    @RequestMapping("/lost/{id}")
    public String lostItem(@PathVariable("id") long id,Model model,RedirectAttributes redirectAttributes){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("No");

//        String roomRentMessage="\""+room.getAddress()+"\""+" is available";
//
//        redirectAttributes.addFlashAttribute("message1", roomRentMessage);

        model.addAttribute("anItem", itemRepository.findOne(id));
        itemRepository.save(item);
        return "redirect:/list";
    }

    //For user registration
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }


    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("User") User user, BindingResult result, Model model){
        model.addAttribute("user",user);
        if(result.hasErrors()){
            return "registration";
        }else{
            userService.saveUser(user);
            model.addAttribute("message","User Account Successfully Created");
        }
        return "index";
    }
}
