package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;


    //상품 목록
    @GetMapping
    public String item(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    //상품 상세 목록
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(
//            @RequestParam String itemName,
//            @RequestParam int price,
//            @RequestParam Integer quantity,
//            Model model
//    ) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

    /*
     * @ModelAttribute("item") Item item 이름을 item 로 지정
     * model.addAttribute("item", item); 모델에 item 이름으로 저장
     *
     * */
//    @PostMapping("/add")
//    public String addItemV2(
//            @ModelAttribute("item") Item item,
//            Model model
//    ) {
//        itemRepository.save(item);
////        model.addAttribute("item", item);     //자동 추가, 생략 가능
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV3(
//            @ModelAttribute Item item,
//            Model model
//    ) {
//        itemRepository.save(item);
//        return "basic/item";
//    }

    //새로고침은 마지막에 수행한 요청을 다시 요청하는 것
    //그러므로 값이 계속해서 저장되는 것을 확인할 수 있음.
//    @PostMapping("/add")
//    public String addItemV4(Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }

    /*
     * addItemV4의 문제의 해결책
     * PRG - Post/Redirect/Get
     * "redirect:/basic/items/" + item.getId(); 방식은
     * URL 인코딩이 안 되기 때문에 사용하는 것은 좋지 않음.
     */
    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /*
     * 테스트용 데이터 추가
     * @PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다
     * */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
