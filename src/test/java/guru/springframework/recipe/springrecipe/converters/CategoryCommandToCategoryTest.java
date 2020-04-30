package guru.springframework.recipe.springrecipe.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import guru.springframework.recipe.springrecipe.commands.CategoryCommand;
import guru.springframework.recipe.springrecipe.domain.Category;

public class CategoryCommandToCategoryTest {
    public static final Long   ID_VALUE    = new Long(1L);
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory  conveter;

    @Test
    public void convert() throws Exception {

        // given
        CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        // when
        Category category = conveter.convert(categoryCommand);

        // then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(conveter.convert(new CategoryCommand()));
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(conveter.convert(null));
    }

    @BeforeEach
    public void setUp() throws Exception {
        conveter = new CategoryCommandToCategory();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
