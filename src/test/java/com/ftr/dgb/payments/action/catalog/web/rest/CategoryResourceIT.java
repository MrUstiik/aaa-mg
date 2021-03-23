package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.ActionCatalogServiceApp;
import com.ftr.dgb.payments.action.catalog.domain.Category;
import com.ftr.dgb.payments.action.catalog.repository.CategoryRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDTO;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.ftr.dgb.payments.action.catalog.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@SpringBootTest(classes = ActionCatalogServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final UUID DEFAULT_PARENT_CATEGORY_ID = UUID.randomUUID();
    private static final UUID UPDATED_PARENT_CATEGORY_ID = UUID.randomUUID();

    private static final String DEFAULT_MCC = "AAAA";
    private static final String UPDATED_MCC = "BBBB";

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_DEFAULT_ORDER_ID = 2;

    private static final String DEFAULT_REGIONS = "AAAAAAAAAA";
    private static final String UPDATED_REGIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ADDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity() {
        Category category = new Category()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .parentCategoryId(DEFAULT_PARENT_CATEGORY_ID)
            .mcc(DEFAULT_MCC)
            .iconUrl(DEFAULT_ICON_URL)
            .defaultOrderId(DEFAULT_DEFAULT_ORDER_ID)
            .regions(DEFAULT_REGIONS)
            .tags(DEFAULT_TAGS)
            .addedDate(DEFAULT_ADDED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return category;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity() {
        Category category = new Category()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .mcc(UPDATED_MCC)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return category;
    }

    @BeforeEach
    public void initTest() {
        categoryRepository.deleteAll();
        category = createEntity();
    }

    @Test
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(DEFAULT_PARENT_CATEGORY_ID);
        assertThat(testCategory.getMcc()).isEqualTo(DEFAULT_MCC);
        assertThat(testCategory.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testCategory.getDefaultOrderId()).isEqualTo(DEFAULT_DEFAULT_ORDER_ID);
        assertThat(testCategory.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testCategory.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCategory.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testCategory.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId("existing_id");
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setUuid(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);


        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setName(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);


        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setEnabled(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);


        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setAddedDate(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);


        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setUpdatedDate(null);

        // Create the Category, which fails.
        CategoryDTO categoryDTO = categoryMapper.toDto(category);


        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Get all the categoryList
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC)))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.parentCategoryId").value(DEFAULT_PARENT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.mcc").value(DEFAULT_MCC))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL))
            .andExpect(jsonPath("$.defaultOrderId").value(DEFAULT_DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.regions").value(DEFAULT_REGIONS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.addedDate").value(sameInstant(DEFAULT_ADDED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        updatedCategory
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .parentCategoryId(UPDATED_PARENT_CATEGORY_ID)
            .mcc(UPDATED_MCC)
            .iconUrl(UPDATED_ICON_URL)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        CategoryDTO categoryDTO = categoryMapper.toDto(updatedCategory);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCategory.getParentCategoryId()).isEqualTo(UPDATED_PARENT_CATEGORY_ID);
        assertThat(testCategory.getMcc()).isEqualTo(UPDATED_MCC);
        assertThat(testCategory.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testCategory.getDefaultOrderId()).isEqualTo(UPDATED_DEFAULT_ORDER_ID);
        assertThat(testCategory.getRegions()).isEqualTo(UPDATED_REGIONS);
        assertThat(testCategory.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testCategory.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testCategory.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Create the Category
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
