Danh sách các thành viên :
- 20110043 - Định Quân 
- 19110219 - Trịnh Công Huynh
- 20161347 - Nguyễn Bùi Minh Nhật

Note ghi chú [Tại đây](https://javier1234559.notion.site/Blog-SpringBoot-111717df189840d1a5d886b680624f85)

Link drive : [Tại đây](https://drive.google.com/drive/u/1/folders/11fKJN6ufK2utgba8H3zy1VMuP7jiPV8d)

Khởi chạy 
Đầu tiên là phải có Inteliji . Các trình IDE khác sợ không thống nhất được vì có nhiều file inteliji tự tạo ra để cấu hình nên ko biết các loại khác chạy được không 

Thứ hai là phải có Mysql workbench 

Clone project về máy 

```
git clone https://github.com/javier1234559/Blog_Spring.git
```

Sau khi clone thành công thì kiểm tra ở file maven xem nó tải hết depencedency cần thiết về hết chưa , gồm có lombok, mysql connector , spring boot starter ,  devtool, jpa ..

Vào mysql workbench tạo database trống cùng tên với project hiện tại spring_blog
![image info](https://file.notion.so/f/s/1f45d27e-762a-4426-a656-6a9a618a77cd/Untitled.png?id=a3bf8b55-5d6c-4b16-af66-8426bbe65977&table=block&spaceId=8a1945d2-d14b-4e07-98bb-3f861a39ff81&expirationTimestamp=1680178917914&signature=CF9qiIcADgf7TRscpO-_CY2hDcJ_xm-HMEUDyMe1EUE&downloadName=Untitled.png)


Đổi lại tên kết nối 

![Untitled](https://file.notion.so/f/s/012f82f1-1cd0-4e78-81c8-ffc3f54fbd65/Untitled.png?id=37681df9-b589-45fe-a40b-93290655647a&table=block&spaceId=8a1945d2-d14b-4e07-98bb-3f861a39ff81&expirationTimestamp=1680179085182&signature=Eof6dmTiNg3DHga5mlATglY7IMWilYAAD9e4PHc92b8&downloadName=Untitled.png)

Quay lại inteliji và khởi chạy nếu maven đã cập nhật xong .