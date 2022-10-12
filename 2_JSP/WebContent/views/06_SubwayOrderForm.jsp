<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>써브웨이에 오신 걸 환영합니다.</title>
<style>
    h1 { color : green; }
    th {
        color : green;
        width : 150px;
        height : 40px;
    }
    .button {
        background-color : green;
        color : white;
        border-color : green;
        border-radius: 3px;
        padding : 5px 20px;
        
     }
    
</style>
</head>
<body>

	<h1 align="center">샌드위치 주문</h1>

    <filedset>
        <table>
            <tr>
                <th>샌드위치 선택</th>
                <td>
                    <select>
                        <option value="sandwich" selected>에그마요</option>
                        <option value="sandwich">로티세리바베큐치킨</option>
                        <option value="sandwich">써브웨이클럽</option>
                        <option value="sandwich">이탈리안비엠티</option>
                        <option value="sandwich">터키베이컨아보카도</option>
                        <option value="sandwich">스테이크&치즈</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>빵 선택</th>
                <td>
                    <input type="radio" name="bread" checked> 화이트
                    <input type="radio" name="bread"> 하티
                    <input type="radio" name="bread"> 파마산오레가노
                    <input type="radio" name="bread"> 위트
                    <input type="radio" name="bread"> 허니오트
                    <input type="radio" name="bread"> 플랫브레드
                </td>
            </tr>
            <tr>
                <th>토핑 선택</th>
                <td>
                    <input type="checkbox" name="topping"> 에그마요
                    <input type="checkbox" name="topping"> 치즈추가
                    <input type="checkbox" name="topping"> 베이컨
                    <input type="checkbox" name="topping"> 페퍼로니
                    <input type="checkbox" name="topping"> 아보카도
                </td>
            </tr>
            <tr>
                <th>야채 선택</th>
                <td>
                    <input type="checkbox" name="vegetable"> 오이
                    <input type="checkbox" name="vegetable"> 양상추
                    <input type="checkbox" name="vegetable"> 올리브
                    <input type="checkbox" name="vegetable"> 할라피뇨
                    <input type="checkbox" name="vegetable"> 양파
                    <input type="checkbox" name="vegetable"> 피망
                    <input type="checkbox" name="vegetable"> 토마토
                    <input type="checkbox" name="vegetable"> 피클
                </td>
            </tr>
            <tr>
                <th>소스 선택</th>
                <td>
                    <input type="checkbox" name="source"> 렌치
                    <input type="checkbox" name="source"> 스위트칠리
                    <input type="checkbox" name="source"> 허니머스타드
                    <input type="checkbox" name="source"> 스위트어니언
                    <input type="checkbox" name="source"> 올리브오일
                    <input type="checkbox" name="source"> 홀드래디쉬
                </td>
            </tr>
            <tr>
                <th>음료 선택</th>
                <td>
                    <input type="radio" name="beverage" checked> 선택안함
                    <input type="radio" name="beverage"> 콜라
                    <input type="radio" name="beverage"> 사이다
                    <input type="radio" name="beverage"> 환타
                    <input type="radio" name="beverage"> 제로콜라
                    <input type="radio" name="beverage"> HOT아메리카노
                    <input type="radio" name="beverage"> ICE아메리카노
                </td>
            </tr>
            <tr>
                <th>쿠키 선택</th>
                <td>
                    <input type="radio" name="cookie" checked> 선택안함
                    <input type="radio" name="cookie"> 초코칩
                    <input type="radio" name="cookie"> 더블초코칩
                    <input type="radio" name="cookie"> 라즈베리치즈케익
                    <input type="radio" name="cookie"> 화이트초코마카다미아
                    <input type="radio" name="cookie"> 오트밀레이즌
                </td>
            </tr>
        </table>
    </filedset>
    <br>
    <input type="submit" value="주문" class="button">
    <input type="reset" class="button">

    

</body>
</html>