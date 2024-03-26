package spring.guro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.guro.dto.BasketDTO;
import spring.guro.entity.*;
import spring.guro.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KioskController {

    private final ProductRepository productRepository; // 제품 정보를 조회하기 위한 리포지토리
    private final BarcodeDataRepository barcodeDataRepository;
    private final TestBranchRepository testBranchRepository;
    private final MemberRepository memberRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderDetailRepository productOrderDetailRepository;
    private final RecipeRepository recipeRepository;
    private final BranchInventoryRepository branchInventoryRepository;

    @PostMapping("/kokee/kiosk")
    public void addOrders(@RequestBody List<BasketDTO> basketDTOS) {

        int poPayment = 0;

        for (BasketDTO basketDTO : basketDTOS) {
            System.out.println("Product Name: " + basketDTO.getPdName());
            System.out.println("Product Id: " + basketDTO.getPdId());
            System.out.println("Product Price: " + basketDTO.getPrice());
            System.out.println("Product Quantity: " + basketDTO.getQuantity());
            System.out.println();
            poPayment += basketDTO.getPrice()*basketDTO.getQuantity();
        }
        //바인딩 된 객체 찾기
        Branch branch = testBranchRepository.findByBranchName("branch1");
        Member member = memberRepository.findByEmail("barnch1@kiosk1.com");
        //주문 삽입
        ProductOrder productOrder = new ProductOrder();
        productOrder.setPoDate(LocalDate.now());
        productOrder.setPoPayment(poPayment);
        productOrder.setBranch(branch);
        productOrder.setMember(member);
        productOrderRepository.save(productOrder);

        for (BasketDTO basketDTO : basketDTOS) {
            //바인딩 된 객체 찾기
            Product product = productRepository.findByPdName(basketDTO.getPdName());

            //save된 productOrder id 확인
            System.out.println(productOrder.getId());
            //주문 상세 삽입
            ProductOrderDetail productOrderDetail = new ProductOrderDetail();
            productOrderDetail.setProduct(product);
            productOrderDetail.setProductOrder(productOrder);
            productOrderDetail.setPodQuantity(basketDTO.getQuantity());
            productOrderDetail.setPodSubtotal(basketDTO.getPrice()*basketDTO.getQuantity());
            productOrderDetailRepository.save(productOrderDetail);

            //주문 상세에 따른 재고 소진
            List<Recipe> recipes = recipeRepository.findByPdId(product.getPdId());
            recipes.forEach(recipe -> {
                BranchInventory inventory = branchInventoryRepository.findByBranchAndIngredientIds(branch.getBranchId(), recipe.getIngredient().getIgId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found in inventory"));
                inventory.setIgQuantity(inventory.getIgQuantity() - recipe.getReQuantity()*basketDTO.getQuantity());
                //매개변수 확인, 재고 소진 확인
                System.out.println(branch.getBranchId());
                System.out.println(recipe.getIngredient().getIgId());
                System.out.println(recipe.getReQuantity()*basketDTO.getQuantity());
                System.out.println(inventory.getIgQuantity());
                branchInventoryRepository.save(inventory);
            });
        }



    }


    // 새로운 /selecttea 엔드포인트를 위한 메소드
    @GetMapping("/selecttea")
    public List<Object> getAllTeas() {
        List<Object> result = new ArrayList<>();
        result.addAll(productRepository.findAll());
        result.addAll(barcodeDataRepository.findAll());
        return result;
        // 모든 제품 정보 조회 및 반환
    }
}
