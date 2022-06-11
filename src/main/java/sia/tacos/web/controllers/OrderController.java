package sia.tacos.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacos.domain.data.repositories.OrderRepository;
import sia.tacos.model.TacoOrder;
import sia.tacos.model.User;

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
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

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
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if(errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
