package selenium;

import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteSelenium
{
    public static void main(String[] args)
    {
        System.setProperty("webdriver.gecko.driver", 
                "C:\\Users\\Ana Miani\\Documents\\NetBeansProjects\\ProjetoLivros\\selenium-java-3.8.1\\geckodriver.exe");

        // Create a new instance of the Firefox driver
        WebDriver driver = new FirefoxDriver();

        //Launch the Online Store Website
        driver.get("http://localhost:8080/Web/Login.jsp");
        driver.findElement(By.name("txtEmail")).sendKeys("ana_miani@hotmail.com");
        driver.findElement(By.name("txtSenha")).sendKeys("123456");
        JOptionPane.showMessageDialog(null, "Fazendo login");
        driver.findElement(By.name("operacao")).click();
        driver.get("http://localhost:8080/Web/ComprarLivros?operacao=CONSULTAR");
        JOptionPane.showMessageDialog(null, "Consultando livros");
        driver.get("http://localhost:8080/Web/DetalhesLivro?txtCodigo=5&operacao=VISUALIZAR");
        JOptionPane.showMessageDialog(null, "Visualizando livro de código 5");
        driver.findElement(By.name("txtQtde")).sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.name("txtQtde")).sendKeys("5");
        JOptionPane.showMessageDialog(null, "Adicionando 5 itens");
        driver.findElement(By.name("operacao")).click();
        JOptionPane.showMessageDialog(null, "Alterando");
        //driver.get("");

        // Close the driver
//        driver.quit();
    }
}
