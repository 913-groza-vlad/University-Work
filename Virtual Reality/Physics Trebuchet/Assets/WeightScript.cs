using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WeightScript : MonoBehaviour
{
    private float movingSpeed = 3.5f;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        float horizontalInput = Input.GetAxis("Horizontal");
        float verticalInput = Input.GetAxis("Vertical");
        float horizontalMovement = horizontalInput * movingSpeed * Time.deltaTime;
        float verticalMovement = verticalInput * movingSpeed * Time.deltaTime;
        transform.Translate(new Vector3(horizontalMovement, 0f, verticalMovement));
    }
}
