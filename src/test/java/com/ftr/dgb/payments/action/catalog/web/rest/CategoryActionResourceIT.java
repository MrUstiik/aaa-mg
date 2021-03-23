package com.ftr.dgb.payments.action.catalog.web.rest;

import com.ftr.dgb.payments.action.catalog.ActionCatalogServiceApp;
import com.ftr.dgb.payments.action.catalog.domain.CategoryAction;
import com.ftr.dgb.payments.action.catalog.repository.CategoryActionRepository;
import com.ftr.dgb.payments.action.catalog.service.CategoryActionService;
import com.ftr.dgb.payments.action.catalog.service.dto.CategoryActionDTO;
import com.ftr.dgb.payments.action.catalog.service.mapper.CategoryActionMapper;

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

import com.ftr.dgb.payments.action.catalog.domain.enumeration.ActionType;
/**
 * Integration tests for the {@link CategoryActionResource} REST controller.
 */
@SpringBootTest(classes = ActionCatalogServiceApp.class)
@AutoConfigureMockMvc
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
    private MockMvc restCategoryActionMockMvc;

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
        categoryActionRepository.deleteAll();
        categoryAction = createEntity();
    }

    @Test
    public void createCategoryAction() throws Exception {
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().size();
        // Create the CategoryAction
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);
        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
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
        int databaseSizeBeforeCreate = categoryActionRepository.findAll().size();

        // Create the CategoryAction with an existing ID
        categoryAction.setId("existing_id");
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setUuid(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setName(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setEnabled(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setCategoryId(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setAddedDate(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryActionRepository.findAll().size();
        // set the field null
        categoryAction.setUpdatedDate(null);

        // Create the CategoryAction, which fails.
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);


        restCategoryActionMockMvc.perform(post("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCategoryActions() throws Exception {
        // Initialize the database
        categoryActionRepository.save(categoryAction);

        // Get all the categoryActionList
        restCategoryActionMockMvc.perform(get("/api/category-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryAction.getId())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].mcc").value(hasItem(DEFAULT_MCC)))
            .andExpect(jsonPath("$.[*].defaultOrderId").value(hasItem(DEFAULT_DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].processId").value(hasItem(DEFAULT_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(sameInstant(DEFAULT_ADDED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    public void getCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.save(categoryAction);

        // Get the categoryAction
        restCategoryActionMockMvc.perform(get("/api/category-actions/{id}", categoryAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoryAction.getId()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.mcc").value(DEFAULT_MCC))
            .andExpect(jsonPath("$.defaultOrderId").value(DEFAULT_DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL))
            .andExpect(jsonPath("$.regions").value(DEFAULT_REGIONS))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.processId").value(DEFAULT_PROCESS_ID))
            .andExpect(jsonPath("$.addedDate").value(sameInstant(DEFAULT_ADDED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    public void getNonExistingCategoryAction() throws Exception {
        // Get the categoryAction
        restCategoryActionMockMvc.perform(get("/api/category-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.save(categoryAction);

        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().size();

        // Update the categoryAction
        CategoryAction updatedCategoryAction = categoryActionRepository.findById(categoryAction.getId()).get();
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
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(updatedCategoryAction);

        restCategoryActionMockMvc.perform(put("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
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
        int databaseSizeBeforeUpdate = categoryActionRepository.findAll().size();

        // Create the CategoryAction
        CategoryActionDTO categoryActionDTO = categoryActionMapper.toDto(categoryAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryActionMockMvc.perform(put("/api/category-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoryActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryAction in the database
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCategoryAction() throws Exception {
        // Initialize the database
        categoryActionRepository.save(categoryAction);

        int databaseSizeBeforeDelete = categoryActionRepository.findAll().size();

        // Delete the categoryAction
        restCategoryActionMockMvc.perform(delete("/api/category-actions/{id}", categoryAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoryAction> categoryActionList = categoryActionRepository.findAll();
        assertThat(categoryActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
