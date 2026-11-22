import java.util.Scanner;

public class Album {
    private String reference;
    private String title;
    private double prix;
    private String author;
    private int stock;

    public Album(String reference,String title,String author,double prix,int quantity){
        this.reference=reference;
        this.title=title;
        this.author=author;
        this.prix=prix;
        this.stock=quantity;
    }
    
    public String getReference()
     { return reference;

      }
    public String getTitle()     
    { return title;

     }
    public String getAuthor()
        { return author;

         }
    public double getPrix()    
    { return prix;

     }
    public int getStock()   
         { return stock;

          }
    
    public void setprix(double newprix){
        if(prix>=0){
         prix=newprix;
        }else{
            System.err.println("invalid price");
        }
    }
 public void setstock(int newstock ){
        if(prix>=0){
         stock=newstock;
        }else{
            System.err.println("invalid stock");
        }
 
    }
    public void display(){
        System.out.println("the album ref :"+ reference +" title :"+ title + " author :"+ author +" price :"+ prix +" stock :"+ stock );
    }
    
    public void increasstock(int inc){
       stock-=inc;
       setstock(stock);

    }
     
    public void decreasstock(int dec){
      stock+=dec;
       setstock(stock);
    }




}

 class Account{
    private String Username;
    private String password;
    private double balance;
    private cart cart; 
    private order[] history;
    private int NBorder=0;
    
    public Account(String Username, String password){
        this.Username=Username;
        this.password=password;
        this.balance=0;
        this.cart = new cart();
this.history = new order[50];
        
    }
    public String getUsername(){
        return Username;
    }
    public boolean checkpassword(String pw) {
    return password.equals(pw);
}
    public void setUsername(String username){
        Username=username;

    }
    public void setpassword(String words){
        password=words;
    }

    public void setbalance(double money){
        if(money>=0){ 
        balance=money;}
        else{System.err.println("invalid balance");}
    }
    

public boolean stockcheckitembyitem(Item item){   //it check one item
        boolean found=false;
        if(item.getnbcopies()<=item.getalbum().getStock()){
            found=true;
        }
        return found;
    }
    public boolean checkcart(){
        boolean confirmed=true;
         if (cart.getnbitem() == 0) { System.out.println("the cart is empty"); return false; }
    if (NBorder >= 50)         { System.out.println("order history is full"); return false; }

        if(balance<cart.getpricetotal()){
              confirmed=false;
           System.out.println("ur account balance is less then the total ");
         }
         for(int i=0;i<=cart.getnbitem();i++){                              //7na 7ebin nmalipuliw l order mbe3d f account b checking nte3 order nkheli l customer ydir update lel cart wey3ewed ylancer order jdid 
            if(!stockcheckitembyitem(cart.getitem(i))){
                if(cart.getitem(i).getalbum().getStock()==0){
                   
                    confirmed=false;
                    System.out.println("sorry the item is out of stock ");
                    //remove item from cart 
                    
                    //reconfirm the order


                }else{   
                    
                    confirmed=false;
                System.out.println("the item number "+i+" is out of stock there is only "+cart.getitem(i).getalbum().getStock()+" left/n ");
                //remove copies wekda (update khir)
                
                }
            }
        }  

        return confirmed;

    }
    
     
    public void confirmorder(){
        if(checkcart()){
            //take care of debit
            double total=cart.getpricetotal();
            balance-=total;
            setbalance(balance);
            //history whatever
            Item[] copy= cart.copyItems();
            int nb=cart.getnbitem();
            history[NBorder]=new order(copy, nb, total);
            NBorder++;
            //take care of stock
            for(int i=0;i<nb;i++){
              copy[i].getalbum().decreasstock(copy[i].getnbcopies());}

            //empty the cart
            cart.empty();
           System.out.println("your order is passed successfuly");
        } 
     else{
        System.out.println("your order has been declined");
     }

    }


}
 
class cart {     //need to check if the items didnt get repeated
    private Item[] items;
    private int NBitem;
    

    public cart(){
       this.items= new Item[10];   //is this declaration right?
       this.NBitem=0;
    }
    public Item getitem(int i){
        return items[i];
    }
    public void addItems(Item item){    //is this a whole new array ?
       if (NBitem<10){
        items[NBitem]=item;
        NBitem++;
       }
       else{
        System.out.println("the cart is full please wait till the next order");
       }
     
    }
    public int getnbitem(){
        return NBitem;
     }
// in Cart
public Item[] copyItems() {
    Item[] copy = new Item[10];
    for (int i = 0; i < NBitem; i++) {
        copy[i] = items[i];
    }
    return copy;
}
//all this just to remove an item bruh 
    public int searchitem(Item item){
       for(int i=0;i<=NBitem;i++){
         if(items[i]==item ){
            return i;
         }
       } 
       return -1;
    }
    public void removeitem(Item item){
       int i=searchitem(item);
       
       while(i!=-1 && i<NBitem){
         items[i]=items[i+1];
       }
         NBitem-=1;
    }
public double getpricetotal(){   // mene3ref 9atelna 3liha wela la 
                     //amount li fel cart
        double total=0;
        for(int i=0;i<10;i++){
         Album album=items[i].getalbum();
         int nbcopies=items[i].getnbcopies();
        total+=  nbcopies*album.getPrix();
        }
        return total;
     }
     public void empty() {
    items = new Item[10];
    NBitem = 0;
}
     
     public void display(){
        for(int i=0;i<10;i++){
          items[i].display();
        }
        System.out.println("the total price of this cart is :"+getpricetotal());   //npaci total hna beh ye3ref ch7el keyen fla carte likhdemha mbe3d fel order hedek yekhdem ghir ldekhel m3a l account wel confirmation
     }

   public Item[] getItem(){
    return items;               //suuuuussssss pass the og array while i need a copy
   }
}
class Item{
    private Album album;
    private int nbcopies;

    public Item(){
        this.album=album;
        this.nbcopies=1;   //by default

    }
   
    public void setcopies(Item item,int nbcopies){
        if(item.album.getStock()>=nbcopies){ 
       item.nbcopies=nbcopies;}
       else{
        System.out.println(" this amount of copies is not available please check the stock");
       }
    }
   public void addcopies(Item item,int add){
    int k=getnbcopies();
    k+=add;
     if(item.album.getStock()>=k){ 
    
    setcopies(item, k);}
   }
public void removecopies(Item item,int rem){
    int k=getnbcopies();
     k-=rem;
    if(item.album.getStock()>=k){ 
   
    setcopies(item, k);}
   }

   public Album getalbum(){
    return album;
   }
   public int getnbcopies(){
    return nbcopies;
   }
    public void display(){
        System.out.println("the album : "+ album + "and the number of copies :"+ nbcopies + "/n");
    }
}

class order{
    private static int counter=1;
    private Item[] items;
    private double total;
    private int number;
    private int nbitem;
    
    public order(Item[] copy,int nbitem,double total){    // esq ndekhel parametre wela la?(getitems m3a getpricetotal)
        this.items= copy;//=getitems;
        this.total=total;   // total nte3 drahem psq hedro fel exo 3la order confirmation 
        this.number=counter++;     // ye7sseb ch7el men keyen order derha lcompte fi7yeteh
        this.nbitem=nbitem;
    }
       
    public int getcounter(){
        return number;
    }
    public double gettotal(){
        return total;
    }
   
    
    public void display(){
        System.out.println("this account ordered :"+counter+" already");
    }
    
    
}


class Bluesay{
    private static final Scanner sc = new Scanner(System.in);
    private Album[] albums;
    private int nbalbum;
    private Account[] accounts;
    private int nbaccount=0;


    public Bluesay(Album[] albums, int nbalbum, Account[] accounts, int nbaccount) {
    this.albums = albums;
    this.nbalbum = nbalbum;
    this.accounts = accounts;
    this.nbaccount = nbaccount;
}


    public int searchalbum(String reference) {
    for (int i = 0; i < nbalbum; i++) {
        if (albums[i].getReference().equals(reference)) return i;
    }
    return -1;
}
    public void addalbum() {
    if (nbalbum >= 500) {
        System.out.println("catalogue is full");
        return;
    }

    System.out.print("title: ");
    String title = sc.nextLine();
    System.out.print("author: ");
    String author = sc.nextLine();
    System.out.print("reference: ");
    String reference = sc.nextLine();

    if (searchalbum(reference) != -1) {
        System.out.println("reference already exists");
        return;
    }

    System.out.print("Quantity: ");
    int qty = sc.nextInt();
    System.out.print("Price: ");
    double price = sc.nextDouble();
    sc.nextLine();                      

    if (price < 0 || qty < 0) {
        System.out.println("invalid price or quantity");
        return;
    }

    albums[nbalbum] = new Album(reference, title, author, price, qty);
    nbalbum++;
}
    public void addcopies(Album albums,int m){
          albums.increasstock(m);
    }
    public void removecopies(Album albums,int k){
         albums.decreasstock(k);
    }
    public void displaycatalogue(){
           for(int i=0;i<nbalbum;i++){
            albums[i].display();
           }
    }
    public void createaccount(){
        if(nbaccount<1000){     //need waaay more checking
              System.out.print("username: ");
    String username = sc.nextLine();
      System.out.print("password: ");
    String password = sc.nextLine();
        accounts[nbaccount]=new Account(username, password);
              System.out.print("balance: ");
    double balance = sc.nextDouble();
    accounts[nbaccount].setbalance(balance);

        }
        else{System.out.println("there is no space for another account");}

    }
    public int searchaccount(String username) {
        for (int i = 0; i < nbaccount; i++)
            if (accounts[i].getUsername().equals(username)) return i;
        return -1;
    }
    public Account openaccount() {
        System.out.print("username: "); String username = sc.nextLine();
        System.out.print("password: "); String password = sc.nextLine();

        int i = searchaccount(username);
        if (i == -1 || !accounts[i].checkpassword(password)) {
            System.out.println("wrong username or password");
            return null;
        }
        return accounts[i];
    }

}
 class Main {
    public static void main(String[] args) {
        Album[] albums = {
            new Album("A1", "Blue Train", "John Coltrane", 15.99, 5),
            new Album("A2", "Kind of Blue", "Miles Davis", 18.50, 3)
        };

        Account[] accounts = {
            new Account("sam", "pass123")
        };

        Bluesay shop = new Bluesay(albums, albums.length, accounts, accounts.length);

        shop.displaycatalogue();

        Account me = shop.openaccount();
        if (me != null) {
            me.confirmorder();
        }
    }
}