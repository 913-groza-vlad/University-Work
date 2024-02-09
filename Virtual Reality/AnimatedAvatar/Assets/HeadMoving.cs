using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HeadMoving : MonoBehaviour
{ 
    // Start is called before the first frame update
    void Start()
    {        
    }

    // Update is called once per frame
    void Update()
    {
        var rayToMouseScreenPoint = Camera.main.ScreenPointToRay(Input.mousePosition);
        var screenPoint = (transform.position - Camera.main.transform.position).magnitude * 0.3f;

        transform.LookAt(rayToMouseScreenPoint.origin + rayToMouseScreenPoint.direction * screenPoint);
    }
}
