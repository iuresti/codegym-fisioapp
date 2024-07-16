package codegym.tequila.fisioapp.controller;

import codegym.tequila.fisioapp.service.TherapyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MVCTherapyController {

    private final TherapyService therapyService;

    public MVCTherapyController(TherapyService therapyService) {
        this.therapyService = therapyService;
    }

    @GetMapping("ui/therapies")
    public ModelAndView listTherapies(@RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) Integer pageIndex,
                                      @RequestParam(required = false) Boolean all,
                                      @RequestParam(required = false) Boolean inactive){
        ModelAndView modelAndView = new ModelAndView("therapies-list");

        modelAndView.addObject("therapies", therapyService.getTherapies(pageSize, pageIndex, all, inactive));

        return modelAndView;
    }

}
