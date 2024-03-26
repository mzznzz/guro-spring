package spring.guro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import spring.guro.dto.OrdersDTO;
import spring.guro.entity.*;
import spring.guro.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrdersController {

    private final OrdersRepository ordersRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final TestBranchRepository testBranchRepository;
    private final MemberRepository memberRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderDetailRepository productOrderDetailRepository;
    private final RecipeRepository recipeRepository;
    private final BranchInventoryRepository branchInventoryRepository;

    @PostMapping("/kokee/orders/{email}")
    @Transactional
    public void addOrders(@PathVariable("email") String email, @RequestBody List<OrdersDTO> getData) {
        for (OrdersDTO getDatum : getData) {
            //기존 코드
            Orders orders = new Orders();
            orders.setProductName(getDatum.getProductName());
            orders.setMount(getDatum.getMount());
            orders.setPrice(getDatum.getPrice());
            orders.setBranchName(getDatum.getBranchName());
            orders.setEmail(getDatum.getEmail());
            orders.setDate(LocalDateTime.now());
            ordersRepository.save(orders);

            //바인딩 된 객체 찾기
            Product product = productRepository.findByPdName(getDatum.getProductName());
            Branch branch = testBranchRepository.findByBranchName(getDatum.getBranchName());
            Member member = memberRepository.findByEmail(getDatum.getEmail());

            //주문 삽입
            ProductOrder productOrder = new ProductOrder();
            productOrder.setBranch(branch);
            productOrder.setMember(member);
            productOrder.setPoPayment(getDatum.getPrice());
            productOrder.setPoDate(LocalDate.now());
            productOrderRepository.save(productOrder);

            //save된 productOrder id 확인
//            System.out.println(productOrder.getId());

            //주문 상세 삽입
            ProductOrderDetail productOrderDetail = new ProductOrderDetail();
            productOrderDetail.setProduct(product);
            productOrderDetail.setProductOrder(productOrder);
            productOrderDetail.setPodQuantity(getDatum.getMount());
            productOrderDetail.setPodSubtotal(getDatum.getPrice()/getDatum.getMount());
            productOrderDetailRepository.save(productOrderDetail);

            //주문 상세에 따른 재고 소진
            List<Recipe> recipes = recipeRepository.findByPdId(product.getPdId());
            recipes.forEach(recipe -> {
                BranchInventory inventory = branchInventoryRepository.findByBranchAndIngredientIds(branch.getBranchId(), recipe.getIngredient().getIgId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found in inventory"));
                inventory.setIgQuantity(inventory.getIgQuantity() - recipe.getReQuantity()*getDatum.getMount());
                //매개변수 확인, 재고 소진 확인
//                System.out.println(branch.getBranchId());
//                System.out.println(recipe.getIngredient().getIgId());
//                System.out.println(recipe.getReQuantity()*getDatum.getMount());
//                System.out.println(inventory.getIgQuantity());
                branchInventoryRepository.save(inventory);
            });

        }
        cartRepository.deleteByEmail(email);

//        getProductName()을 이용해서 해당 상품과 이름이 같은 consumption_info 테이블 참조 해서 row 가져옴
//        row를 가져와서 Consumption 테이블에 getMount 만큼 반복문을 돌려서 데이터 저장

    }

    @GetMapping("/kokee/orders/{email}")
    public List<Orders> getOrders(@PathVariable("email") String email,
                                  @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
                                  @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        Member member = memberRepository.findByEmail(email);

        if(member.getEmail().equals("admin@admin.com")){
            return ordersRepository.findAllByDateBetween(startDate.minusDays(1), endDate);
        }else{
            return ordersRepository.findAllByEmailAndDateBetween(email, startDate.minusDays(1), endDate);
        }
    }


}
