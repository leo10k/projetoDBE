package br.com.fiap.projetodbe.controlles;

@Controller
public class FeedController {

    @RequestMapping("/api/feed")
    @ResponseBody
    public String show(){
        return "Feed"
    }

}