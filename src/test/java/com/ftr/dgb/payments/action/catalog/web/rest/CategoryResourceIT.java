package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.ActionCatalogServiceApp;
import com.ftr.dgb.payments.action.catalog.domain.Category;
import com.ftr.dgb.payments.action.catalog.repository.CategoryRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryDto;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.ftr.dgb.payments.action.catalog.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@SpringBootTest(classes = ActionCatalogServiceApp.class)
@AutoConfigureWebTestClient
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

    private static final String DEFAULT_MCC = "AAAAAAAAAA";
    private static final String UPDATED_MCC = "BBBBBBBBBB";

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
    private WebTestClient webTestClient;

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
        categoryRepository.deleteAll().block();
        category = createEntity();
    }

    @Test
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().collectList().block().size();
        // Create the Category
        CategoryDto categoryDto = categoryMapper.toDto(category);
        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isCreated();

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
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
        int databaseSizeBeforeCreate = categoryRepository.findAll().collectList().block().size();

        // Create the Category with an existing ID
        category.setId("existing_id");
        CategoryDto categoryDto = categoryMapper.toDto(category);

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().collectList().block().size();
        // set the field null
        category.setUuid(null);

        // Create the Category, which fails.
        CategoryDto categoryDto = categoryMapper.toDto(category);


        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().collectList().block().size();
        // set the field null
        category.setName(null);

        // Create the Category, which fails.
        CategoryDto categoryDto = categoryMapper.toDto(category);


        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().collectList().block().size();
        // set the field null
        category.setEnabled(null);

        // Create the Category, which fails.
        CategoryDto categoryDto = categoryMapper.toDto(category);


        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().collectList().block().size();
        // set the field null
        category.setAddedDate(null);

        // Create the Category, which fails.
        CategoryDto categoryDto = categoryMapper.toDto(category);


        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().collectList().block().size();
        // set the field null
        category.setUpdatedDate(null);

        // Create the Category, which fails.
        CategoryDto categoryDto = categoryMapper.toDto(category);


        webTestClient.post().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCategoriesAsStream() {
        // Initialize the database
        categoryRepository.save(category).block();

        List<Category> categoryList = webTestClient.get().uri("/api/categories")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_STREAM_JSON)
            .returnResult(CategoryDto.class)
            .getResponseBody()
            .map(categoryMapper::toEntity)
            .filter(category::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(categoryList).isNotNull();
        assertThat(categoryList).hasSize(1);
        Category testCategory = categoryList.get(0);
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
    public void getAllCategories() {
        // Initialize the database
        categoryRepository.save(category).block();

        // Get all the categoryList
        webTestClient.get().uri("/api/categories?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id").value(hasItem(category.getId()))
            .jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString()))
            .jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue()))
            .jsonPath("$.[*].parentCategoryId").value(hasItem(DEFAULT_PARENT_CATEGORY_ID.toString()))
            .jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC))
            .jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL))
            .jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID))
            .jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS))
            .jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS))
            .jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE)))
            .jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    
    @Test
    public void getCategory() {
        // Initialize the database
        categoryRepository.save(category).block();

        // Get the category
        webTestClient.get().uri("/api/categories/{id}", category.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").value(is(category.getId()))
            .jsonPath("$.uuid").value(is(DEFAULT_UUID.toString()))
            .jsonPath("$.name").value(is(DEFAULT_NAME))
            .jsonPath("$.enabled").value(is(DEFAULT_ENABLED.booleanValue()))
            .jsonPath("$.parentCategoryId").value(is(DEFAULT_PARENT_CATEGORY_ID.toString()))
            .jsonPath("$.mcc").value(is(DEFAULT_MCC))
            .jsonPath("$.iconUrl").value(is(DEFAULT_ICON_URL))
            .jsonPath("$.defaultOrderId").value(is(DEFAULT_DEFAULT_ORDER_ID))
            .jsonPath("$.regions").value(is(DEFAULT_REGIONS))
            .jsonPath("$.tags").value(is(DEFAULT_TAGS))
            .jsonPath("$.addedDate").value(is(sameInstant(DEFAULT_ADDED_DATE)))
            .jsonPath("$.updatedDate").value(is(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    public void getNonExistingCategory() {
        // Get the category
        webTestClient.get().uri("/api/categories/{id}", Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryRepository.save(category).block();

        int databaseSizeBeforeUpdate = categoryRepository.findAll().collectList().block().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).block();
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
        CategoryDto categoryDto = categoryMapper.toDto(updatedCategory);

        webTestClient.put().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isOk();

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
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
        int databaseSizeBeforeUpdate = categoryRepository.findAll().collectList().block().size();

        // Create the Category
        CategoryDto categoryDto = categoryMapper.toDto(category);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient.put().uri("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryDto))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCategory() {
        // Initialize the database
        categoryRepository.save(category).block();

        int databaseSizeBeforeDelete = categoryRepository.findAll().collectList().block().size();

        // Delete the category
        webTestClient.delete().uri("/api/categories/{id}", category.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll().collectList().block();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
