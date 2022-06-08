package sia.tacos.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacos.domain.data.repositories.interfaces.OrderRepository;
import sia.tacos.model.TacoOrder;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    /**
     * The <b>@Valid</b> annotation tells Spring MVC to perform validation on the submitted Taco object after it’s bound to the submitted form data
     * and before the <b>processTaco()</b> method is called. If there are any validation errors,
     * the details of those errors will be captured in an Errors object that’s passed into <b>processTaco()</b>.
     * The first few lines of <b>processTaco()</b> consult the Errors object, asking its <b>hasErrors()</b> method if there are any validation errors.
     * If there are, the method concludes without processing the <b>Taco</b> and returns the <b>"design"</b> view name so that the form is redisplayed.
     * */

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if(errors.hasErrors()) {
            return "orderForm";
        }

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
