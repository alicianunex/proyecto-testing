package com.example.proyectotesting.patterns.creational.builder;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;



class UserTest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean married;

    User usuario;
    @BeforeEach
    void setUp() {
        usuario = new User(1L,"Jesus","Perez","jesusperez@hotmail.com", true);
    }

    @Test
    void getIdTest() {
        Long result = usuario.getId();
        assertEquals(1L,result);
        assertNotEquals(2L, (long) result);
    }
    @Test
    void getFirstNameTest() {
        String result = usuario.getFirstName();
        assertEquals("Jesus",result);
    }

    @Test
    void getLastNameTest() {
        String result = usuario.getLastName();
        assertEquals("Perez",result);
    }
    @Test
    void getEmailTest() {
        String result = usuario.getEmail();
        assertEquals("jesusperez@hotmail.com",result);
    }
    @Test
    void getMarriedTest() {
        boolean result = usuario.getMarried();
        assertTrue(result);
    }
    @Test
    void setIdTest() {
        usuario.setId(2L);
        assertEquals(2L,usuario.getId());
    }
    @Test
    void setFirstNameTest() {
        usuario.setFirstName("Juan");
        assertEquals("Juan",usuario.getFirstName());
    }
    @Test
    void setLastNameTest() {
        usuario.setLastName("Lopez");
        assertEquals("Lopez",usuario.getLastName());
    }
    @Test
    void setEmailTest() {
        usuario.setEmail("juanlopez@hotmail.com");
        assertEquals("juanlopez@hotmail.com",usuario.getEmail());
    }
    @Test
    void setMarriedTest() {
        usuario.setMarried(false);
        assertEquals(false,usuario.getMarried());
    }
    @Test
    void BuilderSetIdTest() {
        new User.Builder().setId(3L);
        assertEquals(1L, usuario.getId());
    }
    @Test
    void BuilderGetIdTest() {
        assertEquals(new User.Builder().getId(),null);
    }
    @Test
    void BuilderSetFirstNameTest() {
        new User.Builder().setFirstName("Maria");
        String firstName = new User.Builder().getFirstName();
        assertEquals(null, firstName);
    }

    @Test
    void BuilderSetLastNameTest() {
        new User.Builder().setLastName("Garcia");
        String lastName = new User.Builder().getLastName();
        assertEquals(null, lastName);
    }

    @Test
    void BuilderSetEmailTest() {
        new User.Builder().setEmail("maria@hotmail.com");
        String email = new User.Builder().getEmail();
        assertEquals(null, email);
    }
    @Test
    void BuilderSetMarriedTest() {
        new User.Builder().setMarried(true);
        Boolean married = new User.Builder().getMarried();
        assertEquals(null, married);
    }


}

