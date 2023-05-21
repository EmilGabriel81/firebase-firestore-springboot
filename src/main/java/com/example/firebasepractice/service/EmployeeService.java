package com.example.firebasepractice.service;

import com.example.firebasepractice.entity.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService {

    private final String COLLECTION_NAME = "employees";

    public Employee getEmployee(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Employee employee = null;
        if(document.exists()){
            employee = document.toObject(Employee.class);
            return employee;
        }else {
            return null;
        }
    }

    public String deleteEmployee(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore
                .collection(COLLECTION_NAME)
                .document(name).delete();
        return "Document with ID "+name+" successfully deleted";
    }

    public String updateEmployee(Employee employee) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(employee.getName()).set(employee);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
       Firestore dbFirestore = FirestoreClient.getFirestore();
       Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
       Iterator<DocumentReference> iterator = documentReference.iterator();
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = null;

        while (iterator.hasNext()){
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot documentSnapshot = future.get();
            employee = documentSnapshot.toObject(Employee.class);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public String addEmployee(Employee employee) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore
                .collection(COLLECTION_NAME)
                .document(employee.getName())
                .set(employee);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
}
