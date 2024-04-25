package com.example.demoV.restController;
import com.example.demoV.model.Product;
import com.example.demoV.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        return service.getList();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping("/delete-by-id")
    public ResponseEntity<?> delete(@RequestParam int pid) {
        return service.delete(pid);
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<?> getProductById(@RequestParam int pid) {
        return service.getProductById(pid);
    }

    @GetMapping("/get-product-by-name")
    public ResponseEntity<?> getListProductByProductName(@RequestParam String name) {
        return service.searchAllByProductName(name);
    }
    @GetMapping("/get-product-by-name-or-des")
    public ResponseEntity<?> searchAllByDescriptionOrProductName(@RequestParam String name,@RequestParam String des  ) {
        return service.searchAllByDescriptionOrProductName(des,name);
    }

    @GetMapping("/get-product-by-des")
    public ResponseEntity<?> searchAllByDescription(@RequestParam String des) {
        return service.searchAllByDescription(des);
    }
}
