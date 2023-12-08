//package com.example.mindscribe;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//import android.text.Editable;
//import android.widget.EditText;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Volley.class})
//public class LoginActivityTest {
//
//    @Mock
//    private EditText mockUsernameEditText;
//
//    @Mock
//    private EditText mockPasswordEditText;
//
//    @Mock
//    private LoginActivity mockLoginActivity;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testLoginSuccess() throws Exception {
//        // Arrange
//        String testUsername = "testUser";
//        String testPassword = "testPassword";
//
//        when(mockUsernameEditText.getText()).thenReturn(mock(Editable.class));
//        when(mockUsernameEditText.getText().toString()).thenReturn(testUsername);
//
//        when(mockPasswordEditText.getText()).thenReturn(mock(Editable.class));
//        when(mockPasswordEditText.getText().toString()).thenReturn(testPassword);
//
//        // Mock a custom RequestQueue
//        RequestQueue mockRequestQueue = mock(RequestQueue.class);
//
//        // Mock the static method Volley.newRequestQueue
//        PowerMockito.mockStatic(Volley.class);
//        when(Volley.newRequestQueue(any())).thenReturn(mockRequestQueue);
//
//        // Act
//        mockLoginActivity.onLoginBtnClick(null);
//
//        // Assert
//        // Add your assertions here, e.g., verify that the request was added to the queue
//        verify(mockRequestQueue).add(any());
//    }
//}