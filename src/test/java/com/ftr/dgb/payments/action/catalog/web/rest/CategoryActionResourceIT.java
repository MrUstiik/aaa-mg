package com.ftr.dgb.payments.action.catalog.web.rest;

import static com.ftr.dgb.payments.action.catalog.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.ftr.dgb.payments.action.catalog.ActionCatalogServiceApp;
import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;
import com.ftr.dgb.payments.action.catalog.domain.enumeration.ActionType;
import com.ftr.dgb.payments.action.catalog.repository.CategoryActionRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDto;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryActionMapper;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link CategoryActionResource} REST controller.
 */
@SpringBootTest(classes = ActionCatalogServiceApp.class)
@AutoConfigureWebTestClient
@WithMockUser
public class CategoryActionResourceIT {
    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.BILLPAY;
    private static final ActionType UPDATED_ACTION_TYPE = ActionType.BILLPAY;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final UUID DEFAULT_CATEGORY_ID = UUID.randomUUID();
    private static final UUID UPDATED_CATEGORY_ID = UUID.randomUUID();

    private static final String DEFAULT_MCC = "AAAA";
    private static final String UPDATED_MCC = "BBBB";

    private static final Integer DEFAULT_DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_DEFAULT_ORDER_ID = 2;

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REGIONS = "AAAAAAAAAA";
    private static final String UPDATED_REGIONS = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ADDED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADDED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CategoryActionRepository categoryActionRepository;

    @Autowired
    private CategoryActionMapper categoryActionMapper;

    @Autowired
    private CategoryActionService categoryActionService;

    @Autowired
    private WebTestClient webTestClient;

    private CategoryAction categoryAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAction createEntity() {
        CategoryAction categoryAction = new CategoryAction()
            .uuid(DEFAULT_UUID)
            .actionType(DEFAULT_ACTION_TYPE)
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .categoryId(DEFAULT_CATEGORY_ID)
            .mcc(DEFAULT_MCC)
            .defaultOrderId(DEFAULT_DEFAULT_ORDER_ID)
            .iconUrl(DEFAULT_ICON_URL)
            .regions(DEFAULT_REGIONS)
            .tags(DEFAULT_TAGS)
            .source(DEFAULT_SOURCE)
            .processId(DEFAULT_PROCESS_ID)
            .addedDate(DEFAULT_ADDED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return categoryAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoryAction createUpdatedEntity() {
        CategoryAction categoryAction = new CategoryAction()
            .uuid(UPDATED_UUID)
            .actionType(UPDATED_ACTION_TYPE)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .categoryId(UPDATED_CATEGORY_ID)
            .mcc(UPDATED_MCC)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .iconUrl(UPDATED_ICON_URL)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .source(UPDATED_SOURCE)
            .processId(UPDATED_PROCESS_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return categoryAction;
    }

    @BeforeEach
    public void initTest() {
        categoryActionRepository.deleteAll().block();
        categoryAction = createEntity();
    }

    @Test
    public void createCategoryAction() throws Exception {
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().collectList().block().size();
        // Create the CategoryAction
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);
        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryAction testCategoryAction = categoryActionList.get(categoryActionList.size() - 1);
        assertThat(testCategoryAction.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCategoryAction.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testCategoryAction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategoryAction.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCategoryAction.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryAction.getMcc()).isEqualTo(DEFAULT_MCC);
        assertThat(testCategoryAction.getDefaultOrderId()).isEqualTo(DEFAULT_DEFAULT_ORDER_ID);
        assertThat(testCategoryAction.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testCategoryAction.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testCategoryAction.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCategoryAction.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCategoryAction.getProcessId()).isEqualTo(DEFAULT_PROCESS_ID);
        assertThat(testCategoryAction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testCategoryAction.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    public void createCategoryActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().collectList().block().size();

        // Create the CategoryAction with an existing ID
        categoryAction.setId("existing_id");
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setUuid(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setName(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setEnabled(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setCategoryId(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setAddedDate(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().collectList().block().size();
        // set the field null
        categoryAction.setUpdatedDate(null);

        // Create the CategoryAction, which fails.
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        webTestClient
            .post()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCategoryActionsAsStream() {
        // Initialize the database
        categoryActionRepository.save(categoryAction).block();

        List<CategoryAction> categoryActionList = webTestClient
            .get()
            .uri("/api/category-actions")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_STREAM_JSON)
            .returnResult(CategoryActionDto.class)
            .getResponseBody()
            .map(categoryActionMapper::toEntity)
            .filter(categoryAction::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(categoryActionList).isNotNull();
        assertThat(categoryActionList).hasSize(1);
        CategoryAction testCategoryAction = categoryActionList.get(0);
        assertThat(testCategoryAction.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCategoryAction.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testCategoryAction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategoryAction.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCategoryAction.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategoryAction.getMcc()).isEqualTo(DEFAULT_MCC);
        assertThat(testCategoryAction.getDefaultOrderId()).isEqualTo(DEFAULT_DEFAULT_ORDER_ID);
        assertThat(testCategoryAction.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testCategoryAction.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testCategoryAction.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testCategoryAction.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCategoryAction.getProcessId()).isEqualTo(DEFAULT_PROCESS_ID);
        assertThat(testCategoryAction.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testCategoryAction.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    public void getAllCategoryActions() {
        // Initialize the database
        categoryActionRepository.save(categoryAction).block();

        // Get all the categoryActionList
        webTestClient
            .get()
            .uri("/api/category-actions?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(categoryAction.getId()))
            .jsonPath("$.[*].uuid")
            .value(hasItem(DEFAULT_UUID.toString()))
            .jsonPath("$.[*].actionType")
            .value(hasItem(DEFAULT_ACTION_TYPE.toString()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].enabled")
            .value(hasItem(DEFAULT_ENABLED.booleanValue()))
            .jsonPath("$.[*].categoryId")
            .value(hasItem(DEFAULT_CATEGORY_ID.toString()))
            .jsonPath("$.[*].mcc")
            .value(hasItem(DEFAULT_MCC))
            .jsonPath("$.[*].defaultOrderId")
            .value(hasItem(DEFAULT_DEFAULT_ORDER_ID))
            .jsonPath("$.[*].iconUrl")
            .value(hasItem(DEFAULT_ICON_URL))
            .jsonPath("$.[*].regions")
            .value(hasItem(DEFAULT_REGIONS))
            .jsonPath("$.[*].tags")
            .value(hasItem(DEFAULT_TAGS))
            .jsonPath("$.[*].source")
            .value(hasItem(DEFAULT_SOURCE))
            .jsonPath("$.[*].processId")
            .value(hasItem(DEFAULT_PROCESS_ID))
            .jsonPath("$.[*].addedDate")
            .value(hasItem(sameInstant(DEFAULT_ADDED_DATE)))
            .jsonPath("$.[*].updatedDate")
            .value(hasItem(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    public void getCategoryAction() {
        // Initialize the database
        categoryActionRepository.save(categoryAction).block();

        // Get the categoryAction
        webTestClient
            .get()
            .uri("/api/category-actions/{id}", categoryAction.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(categoryAction.getId()))
            .jsonPath("$.uuid")
            .value(is(DEFAULT_UUID.toString()))
            .jsonPath("$.actionType")
            .value(is(DEFAULT_ACTION_TYPE.toString()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.enabled")
            .value(is(DEFAULT_ENABLED.booleanValue()))
            .jsonPath("$.categoryId")
            .value(is(DEFAULT_CATEGORY_ID.toString()))
            .jsonPath("$.mcc")
            .value(is(DEFAULT_MCC))
            .jsonPath("$.defaultOrderId")
            .value(is(DEFAULT_DEFAULT_ORDER_ID))
            .jsonPath("$.iconUrl")
            .value(is(DEFAULT_ICON_URL))
            .jsonPath("$.regions")
            .value(is(DEFAULT_REGIONS))
            .jsonPath("$.tags")
            .value(is(DEFAULT_TAGS))
            .jsonPath("$.source")
            .value(is(DEFAULT_SOURCE))
            .jsonPath("$.processId")
            .value(is(DEFAULT_PROCESS_ID))
            .jsonPath("$.addedDate")
            .value(is(sameInstant(DEFAULT_ADDED_DATE)))
            .jsonPath("$.updatedDate")
            .value(is(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    public void getNonExistingCategoryAction() {
        // Get the categoryAction
        webTestClient
            .get()
            .uri("/api/category-actions/{id}", Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    public void updateCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.save(categoryAction).block();

        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().collectList().block().size();

        // Update the categoryAction
        CategoryAction updatedCategoryAction = categoryActionRepository.findById(categoryAction.getId()).block();
        updatedCategoryAction
            .uuid(UPDATED_UUID)
            .actionType(UPDATED_ACTION_TYPE)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .categoryId(UPDATED_CATEGORY_ID)
            .mcc(UPDATED_MCC)
            .defaultOrderId(UPDATED_DEFAULT_ORDER_ID)
            .iconUrl(UPDATED_ICON_URL)
            .regions(UPDATED_REGIONS)
            .tags(UPDATED_TAGS)
            .source(UPDATED_SOURCE)
            .processId(UPDATED_PROCESS_ID)
            .addedDate(UPDATED_ADDED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(updatedCategoryAction);

        webTestClient
            .put()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeUpdate);
        CategoryAction testCategoryAction = categoryActionList.get(categoryActionList.size() - 1);
        assertThat(testCategoryAction.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCategoryAction.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testCategoryAction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategoryAction.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCategoryAction.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategoryAction.getMcc()).isEqualTo(UPDATED_MCC);
        assertThat(testCategoryAction.getDefaultOrderId()).isEqualTo(UPDATED_DEFAULT_ORDER_ID);
        assertThat(testCategoryAction.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testCategoryAction.getRegions()).isEqualTo(UPDATED_REGIONS);
        assertThat(testCategoryAction.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testCategoryAction.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCategoryAction.getProcessId()).isEqualTo(UPDATED_PROCESS_ID);
        assertThat(testCategoryAction.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testCategoryAction.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    public void updateNonExistingCategoryAction() throws Exception {
        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().collectList().block().size();

        // Create the CategoryAction
        CategoryActionDto categoryActionDto = categoryActionMapper.toDto(categoryAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(categoryActionDto))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCategoryAction() {
        // Initialize the database
        categoryActionRepository.save(categoryAction).block();

        int databaseSizeBeforeDelete = categoryActionRepository.findAll().collectList().block().size();

        // Delete the categoryAction
        webTestClient
            .delete()
            .uri("/api/category-actions/{id}", categoryAction.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll().collectList().block();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
