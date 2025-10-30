# 🧠 ShopCartPro – Backend Documentation

This file documents key issues encountered during backend development (Spring Boot + JPA) and how they were fixed.  
Keeping a clear record like this helps maintain code quality, avoid regressions, and understand architectural decisions. 💪

---

## 1 LazyInitializationException / ByteBuddyInterceptor JSON Error

**❌ Issue Summary:**  
When using `FetchType.LAZY` (default for `@ManyToOne` or `@OneToMany`), Hibernate doesn’t load the related entity (like `Product` or `OrderItems`) until it’s actually accessed within an active session.

When we tried to return these entities as JSON after the session was closed,  
Jackson couldn’t serialize the uninitialized lazy proxies (e.g., `Product$HibernateProxy`, `ByteBuddyInterceptor`).

* When using FetchType.LAZY (default for @ManyToOne or @OneToMany),
* Hibernate doesn't load the related entity (like Product or OrderItems)
* until it's actually accessed within an active session.
*
* When we tried to return these entities as JSON after the session closed,
* Jackson couldn't serialize the uninitialized lazy proxies
* (like Product$HibernateProxy, ByteBuddyInterceptor).

**⚠️ Errors Seen:**
* - "No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor"
* - "could not initialize proxy - no Session"

**🧠 Why It Happened:**
- Hibernate keeps related entities unloaded (`lazy`) to improve performance.
- When JSON serialization happens after the transaction is done, the proxy object no longer has a live session to fetch real data.
- Jackson then fails while serializing those proxy placeholders.

**✅ Fix Applied:**
1️⃣ Changed `FetchType` to **EAGER** for essential relationships  
(e.g., `Order → OrderItems`, `OrderItem → Product`, `Cart → Product`)  
so data loads immediately before serialization.

2️⃣ Introduced **DTOs** (Data Transfer Objects) to avoid returning JPA entities directly.  
This ensures that even with LAZY fetch types, API responses remain clean and serializable.

**💡 Result:**  
All required data now loads before JSON conversion,  
and serialization works cleanly without proxy or session issues.

## 2 Infinite JSON Recursion (Stack Overflow / 3000+ Line Response)

**❌ Issue Summary:**  
When returning `Order` entities directly, each `Order` contained a list of `OrderItems`,  
and each `OrderItem` had a reference back to its parent `Order`.

This caused Jackson to go into an **infinite loop**:

* When we returned Order entities directly, each Order contained a list of OrderItems,
* and each OrderItem had a reference back to its Order.
*
* This caused Jackson (JSON serializer) to go in an infinite loop:
* Order → OrderItem → Order → OrderItem → ...

* ⚠️ Error seen:
* "Document nesting depth (1001) exceeds the maximum allowed (1000)"

**🧠 Why It Happened:**
- Bidirectional relationships (`@OneToMany` + `@ManyToOne`) create circular object graphs.
- Jackson recursively serialized the same linked objects endlessly.

**✅ Fix Applied:**
- Introduced **DTOs** (`OrderResponse`, `OrderItemResponse`) to break circular references.
- to break circular references and return only the necessary fields to the frontend.
- Controllers now return only necessary fields to the frontend.
- Entities (`Order`, `OrderItem`) are used internally for persistence only.

**💡 Result:**  
Controllers now return **clean, finite JSON** responses — no recursion, no Hibernate proxy errors.

## 3 Lombok Annotations Not Working

**❌ Issue Summary:**  
Lombok annotations such as `@Data`, `@Builder`, and `@NoArgsConstructor` were not generating getters/setters or constructors.

**⚠️ Errors Seen:**
- Compilation errors for missing getters/setters
- IntelliJ not recognizing Lombok annotations

**🧠 Why It Happened:**
- The Lombok plugin was not installed/enabled in IntelliJ IDEA.
- Annotation processing was disabled, preventing Lombok from generating code.

**✅ Fix Applied:**  
1️⃣ Installed the **Lombok plugin** in IntelliJ IDEA  
*(File → Settings → Plugins → Search “Lombok” → Install → Restart)*

2️⃣ Enabled annotation processing  
*(File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable)*

**💡 Result:**  
Lombok now automatically generates constructors, getters, setters, and builders.  
Reduces boilerplate code and keeps entities clean.

## ✨ Notes for Future Development

- Prefer **DTOs** for all responses. Avoid returning raw JPA entities from controllers.
- Use `@JsonIgnore` on back-references if returning entities is unavoidable.
- Keep `FetchType.LAZY` for performance where possible, but handle serialization via DTOs.
- Always enable annotation processing if Lombok is used.
- Document every critical bug + fix here for future contributors.

---

💬 *Maintained by:* **Chandana Vanam**  
📅 *Last Updated:* **October 2025**  
🧩 *Modules Covered:* Product, Cart, Wishlist, Order 