package com.lostandfound_wc.demo;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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








    @RequestMapping("showuserlist")
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







//
//    @RequestMapping(value="/additemtouser/{id}", method = RequestMethod.GET)
//    public String addItemToUser(Model model, @PathVariable("id") long id)
//    {
//        model.addAttribute("itemobject",itemRepository.findAll());
//        model.addAttribute("user", userRepository.findOne(id));
//        return"additemtouser";
//    }


//    @RequestMapping(value="/user/{id}/items", method=RequestMethod.GET)
//    public String userAddItems(@PathVariable Long id, Model model) {
//        Item item = itemRepository.findOne(id);
//        User user = userRepository.findOne(id);
//
//        if (user != null) {
//            if (!user.hasItem(item)) {
//                user.getMyItems().add(item);
//            }
//            userRepository.save(user);
//            model.addAttribute("user", userRepository.findOne(id));
//            model.addAttribute("items", itemRepository.findAll());
//            return "redirect:/list";
//        }
//        return "redirect:/list";
//    }






//
//    @GetMapping("/additemtouser/{id}")
//    public String addItemToUser(Model model, @PathVariable("id") long id)
//    {
//        model.addAttribute("itemobject",itemRepository.findOne(id));
//        model.addAttribute("users", userRepository.findAll());
//        return"additemtouser";
//    }
//









//
//    @PostMapping("/additemtouser")
//    public String showItemToUser(HttpServletRequest request, Model model)
//    {
//        String userid = request.getParameter("userid");
//        model.addAttribute("newuseritem",userRepository.findOne(new Long(userid)));
//
//        //Make items disappear from add form when they are already included (Set already makes it impossible to add multiple)
//        //model.addAttribute("skillList",skillRepository.findAll());
//
//        return "userlist";
//    }













    @PostMapping("/process")
    public String processItem(@Valid @ModelAttribute("item") Item item,BindingResult result){

        if (result.hasErrors()) {
            return "additem";
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
    public String currentListings( Model model){
        model.addAttribute("items",itemRepository.findAll());
        return"currentlist";
    }


    @RequestMapping("/adminlist/{id}")
    public String allListings(@PathVariable("id") long id,Model model){
        model.addAttribute("items",itemRepository.findAll());
        model.addAttribute("users",userRepository.findOne(id));
        return"adminlist";
    }

    @RequestMapping("/userlist/{id}")
    public String usersListings(@PathVariable("id") long id,Model model){
        model.addAttribute("users",userRepository.findOne(id));
        model.addAttribute("items",itemRepository.findAll());
        return"userlist";
    }

    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable("id")long id, Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        model.addAttribute("user",userRepository.findUserById(id));
        return "showitemdetails";
    }

    @RequestMapping("/update/{id}")
    public String updateDetail(@PathVariable("id")long id,Model model){
        model.addAttribute("item",itemRepository.findOne(id));
        return "add";
    }

    @RequestMapping("/found/{id}")
    public String foundItem(@PathVariable("id") long id,Model model){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("Yes");

        model.addAttribute("anItem", itemRepository.findOne(id));
        itemRepository.save(item);
        return "redirect:/list";
    }
    @RequestMapping("/lost/{id}")
    public String lostItem(@PathVariable("id") long id,Model model){
        model.addAttribute("item",itemRepository.findOne(id));

        Item item=itemRepository.findOne(id);

        item.setFound("No");

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



    @GetMapping ("/search")
    public String getSearch(){
        return "search";
    }

    @PostMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model){
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("items", itemRepository.findAllByAliasContainingIgnoreCase (searchString));
        return "listbyalias";
    }


}
