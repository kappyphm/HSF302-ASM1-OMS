package crs.hsf302.assignment1.controller; // Đổi package theo project của bạn

import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid; // Nếu dùng javax thì đổi thành javax.validation
import jakarta.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    // --- 1. MOCK DATA (GIẢ LẬP DB) ---
    private List<DepartmentDto> db = new ArrayList<>();
    private List<ManagerDto> managers = new ArrayList<>();

    public DepartmentController() {
        // Init Managers
        managers.add(new ManagerDto("HungNM17", "Nguyễn Mạnh Hùng"));
        managers.add(new ManagerDto("VanNT90", "Nguyễn Thanh Vân"));
        managers.add(new ManagerDto("VuNL17", "Lê Nguyên Vũ"));
        managers.add(new ManagerDto("Admin", "Administrator"));

        // Init Departments
        db.add(new DepartmentDto(1L, "DEP01", "Nhân sự", "HungNM17", 12, "Phòng tuyển dụng và đào tạo"));
        db.add(new DepartmentDto(2L, "DEP02", "Kỹ thuật", "VuNL17", 45, "Phòng phát triển phần mềm"));
        db.add(new DepartmentDto(3L, "DEP03", "Bán hàng", "VanNT90", 30, "Phòng kinh doanh nội địa"));
        db.add(new DepartmentDto(4L, "DEP04", "Marketing", "HungNM17", 8, "Quảng cáo và thương hiệu"));
        db.add(new DepartmentDto(5L, "DEP05", "Kế toán", "VanNT90", 5, "Tài chính và thuế"));
        // Thêm data để test phân trang
        for (int i = 6; i <= 15; i++) {
            db.add(new DepartmentDto((long) i, "DEP0" + i, "Phòng ban " + i, "Admin", i * 2, "Mô tả mẫu"));
        }
    }

    // --- 2. GET: HIỂN THỊ DANH SÁCH & FORM ---
    @GetMapping
    public String viewDepartments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false, defaultValue = "create") String mode, // create, view, edit
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "name") String sortField,
            Model model) {

        // A. XỬ LÝ LIST (Search + Sort + Paging)
        List<DepartmentDto> filtered = db;

        // 1. Search
        if (search != null && !search.isEmpty()) {
            filtered = db.stream()
                    .filter(d -> d.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // 2. Sort (Giả lập sort theo tên và số lượng)
        if ("desc".equals(sortDir)) {
            if ("name".equals(sortField)) filtered.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
            if ("assetCount".equals(sortField)) filtered.sort((o1, o2) -> o2.getAssetCount().compareTo(o1.getAssetCount()));
        } else {
            if ("name".equals(sortField)) filtered.sort(Comparator.comparing(DepartmentDto::getName));
            if ("assetCount".equals(sortField)) filtered.sort(Comparator.comparing(DepartmentDto::getAssetCount));
        }

        // 3. Paging (PageSize = 5)
        int pageSize = 5;
        int start = Math.min((int) PageRequest.of(page, pageSize).getOffset(), filtered.size());
        int end = Math.min((start + pageSize), filtered.size());
        Page<DepartmentDto> departmentPage = new PageImpl<>(filtered.subList(start, end), PageRequest.of(page, pageSize), filtered.size());

        // B. XỬ LÝ FORM BÊN PHẢI
        DepartmentDto formDto = new DepartmentDto();
        if (id != null) {
            // Tìm trong DB giả
            formDto = db.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(new DepartmentDto());
        } else {
            mode = "create"; // Nếu ko có ID thì force về create
        }

        // C. TRUYỀN DATA RA VIEW
        model.addAttribute("departmentPage", departmentPage);
        model.addAttribute("managerList", managers);
        model.addAttribute("departmentDto", formDto);
        model.addAttribute("mode", mode);
        model.addAttribute("sortDir", sortDir);

        // Giữ lại tham số search để hiển thị trên ô input
        model.addAttribute("param.search", search);

        return "department"; // Tên file HTML của bạn
    }

    // --- 3. POST: TẠO MỚI ---
    @PostMapping(params = "add")
    public String create(@Valid @ModelAttribute DepartmentDto dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return reloadPageWithErrors(model, dto, "create");
        }

        // Logic Save giả
        dto.setId(System.currentTimeMillis()); // Fake ID
        dto.setCode("DEP" + (db.size() + 1));
        dto.setAssetCount(0); // Mặc định 0
        db.add(0, dto); // Add vào đầu danh sách

        redirectAttributes.addFlashAttribute("successMessage", "Tạo mới phòng ban thành công!");
        return "redirect:/departments";
    }

    // --- 4. POST: CẬP NHẬT ---
    @PostMapping(params = "save")
    public String update(@Valid @ModelAttribute DepartmentDto dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return reloadPageWithErrors(model, dto, "edit");
        }

        // Logic Update giả
        for (int i = 0; i < db.size(); i++) {
            if (db.get(i).getId().equals(dto.getId())) {
                DepartmentDto old = db.get(i);
                dto.setCode(old.getCode()); // Giữ nguyên mã
                dto.setAssetCount(old.getAssetCount()); // Giữ nguyên tài sản
                db.set(i, dto);
                break;
            }
        }

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/departments";
    }

    // --- 5. POST: XÓA ---
    @PostMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam(required = false) String delete, // check param ?delete
                         RedirectAttributes redirectAttributes) {

        db.removeIf(d -> d.getId().equals(id));
        redirectAttributes.addFlashAttribute("errorMessage", "Đã xóa phòng ban khỏi hệ thống.");
        return "redirect:/departments";
    }

    // Helper để reload trang khi có lỗi validate (giữ nguyên list)
    private String reloadPageWithErrors(Model model, DepartmentDto dto, String mode) {
        // Cần load lại list vì return string view sẽ mất data list
        Page<DepartmentDto> page = new PageImpl<>(db.subList(0, Math.min(5, db.size())), PageRequest.of(0, 5), db.size());
        model.addAttribute("departmentPage", page);
        model.addAttribute("managerList", managers);
        model.addAttribute("departmentDto", dto);
        model.addAttribute("mode", mode);
        model.addAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin nhập!");
        return "department";
    }

    // --- DTO CLASSES (Inner Class cho gọn) ---
    public static class DepartmentDto {
        private Long id;
        private String code;

        @NotEmpty(message = "Tên phòng ban không được để trống")
        private String name;

        @NotEmpty(message = "Vui lòng chọn quản lý")
        private String managerUsername;

        private Integer assetCount;
        private String description;

        // Constructor, Getters, Setters
        public DepartmentDto() {}
        public DepartmentDto(Long id, String code, String name, String managerUsername, Integer assetCount, String description) {
            this.id = id; this.code = code; this.name = name; this.managerUsername = managerUsername; this.assetCount = assetCount; this.description = description;
        }
        // Getter Setter standard
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getManagerUsername() { return managerUsername; }
        public void setManagerUsername(String managerUsername) { this.managerUsername = managerUsername; }
        public Integer getAssetCount() { return assetCount; }
        public void setAssetCount(Integer assetCount) { this.assetCount = assetCount; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class ManagerDto {
        private String username;
        private String fullName;

        public ManagerDto(String username, String fullName) {
            this.username = username; this.fullName = fullName;
        }
        public String getUsername() { return username; }
        public String getFullName() { return fullName; }
    }
}